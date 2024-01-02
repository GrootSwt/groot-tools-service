package com.groot.business.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.entity.MemorandumAndFile;
import com.groot.business.bean.enums.MemorandumContentType;
import com.groot.business.bean.enums.MemorandumOperationType;
import com.groot.business.bean.response.FileResponse;
import com.groot.business.bean.response.MemorandumResponse;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.mapper.FileMapper;
import com.groot.business.mapper.MemorandumMapper;
import com.groot.business.model.FileModel;
import com.groot.business.model.Memorandum;
import com.groot.business.service.FileService;
import com.groot.business.service.MemorandumService;
import com.groot.business.utils.CommonUtil;
import com.groot.business.utils.WSUtil;
import com.groot.business.ws.MemorandumHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemorandumServiceImpl implements MemorandumService {

    private final MemorandumMapper memorandumMapper;

    private final FileService fileService;

    private final FileMapper fileMapper;

    private final MemorandumHandler memorandumHandler;

    private final ObjectMapper objectMapper;
    public MemorandumServiceImpl(final MemorandumMapper memorandumMapper,
                                 final FileService fileService,
                                 final FileMapper fileMapper,
                                 final MemorandumHandler memorandumHandler,
                                 final ObjectMapper objectMapper) {
        this.memorandumMapper = memorandumMapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
        this.memorandumHandler = memorandumHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<MemorandumResponse> list(String userId) {
        if (userId == null) {
            userId = StpUtil.getLoginIdAsString();
        }
        List<MemorandumAndFile> memorandumAndFileList = memorandumMapper.listByUserId(userId);
        List<MemorandumResponse> memorandumResponseList = new ArrayList<>();
        memorandumAndFileList.forEach(item -> {
            MemorandumResponse memorandumResponse = MemorandumResponse
                    .builder()
                    .id(item.getId())
                    .userId(item.getUserId())
                    .content(item.getContent())
                    .contentType(item.getContentType())
                    .createTime(item.getCreateTime())
                    .updateTime(item.getUpdateTime())
                    .build();
            if (item.getContentType() == MemorandumContentType.FILE) {
                FileResponse fileResponse = FileResponse
                        .builder()
                        .id(item.getFileId())
                        .originalName(item.getFileOriginalName())
                        .createTime(item.getFileCreateTime())
                        .updateTime(item.getFileUpdateTime())
                        .deleted(item.isFileDeleted())
                        .build();
                memorandumResponse.setFile(fileResponse);
            }
            memorandumResponseList.add(memorandumResponse);
        });
        return memorandumResponseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Memorandum memorandum) {
        memorandumMapper.insert(memorandum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) throws IOException {
        Memorandum memorandum = memorandumMapper.selectById(id);
        if (null == memorandum) {
            throw new BusinessRuntimeException("备忘录不存在", 404);
        }
        if (memorandum.getDeleted()) {
            throw new BusinessRuntimeException("备忘录已经删除", 400);
        }
        if (memorandum.getContentType() == MemorandumContentType.TEXT) {
            memorandumMapper.deleteById(id);
        } else {
            String fileId = memorandum.getContent();
            FileModel fileModel = fileMapper.selectById(fileId);
            if (!fileModel.getDeleted() && !ObjectUtils.isEmpty(fileModel.getLocationUrl())) {
                CommonUtil.deleteFileAndParentDirIfEmpty(Paths.get(fileModel.getLocationUrl()));
                fileMapper.deleteById(fileId);
            }
            memorandumMapper.deleteById(id);
        }
        List<MemorandumResponse> memorandums = this.list(null);
        this.sendAll(memorandums);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFile(MultipartFile file) throws IOException {
        String userId = StpUtil.getLoginIdAsString();
        FileModel fileModel = fileService.upload(file);
        Memorandum memorandum = new Memorandum();
        memorandum.setUserId(userId);
        memorandum.setContent(fileModel.getId());
        memorandum.setContentType(MemorandumContentType.FILE);
        memorandumMapper.insert(memorandum);
        List<MemorandumResponse> memorandums = this.list(null);
        this.sendAll(memorandums);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoDeleteOutdatedFileTypeMemorandum() throws IOException {
        List<Memorandum> memorandumList = memorandumMapper.listOutdatedFileTypeMemorandum();
        if (!memorandumList.isEmpty()) {
            Set<String> userIdSet = memorandumList.stream().map(Memorandum::getUserId).collect(Collectors.toSet());
            List<String> fileIdList = memorandumList.stream().map(Memorandum::getContent).toList();
            List<FileModel> fileModelList = fileMapper.selectBatchIds(fileIdList);
            fileMapper.deleteBatchIds(fileIdList);
            List<String> memorandumIdList = memorandumList.stream().map(Memorandum::getId).toList();
            memorandumMapper.deleteBatchIds(memorandumIdList);
            fileModelList.forEach(fileModel -> {
                CommonUtil.deleteFileAndParentDirIfEmpty(Paths.get(fileModel.getLocationUrl()));
            });
            this.sendAllByUserIds(userIdSet);
        }
        log.info("备忘录过期文件自动删除成功，删除文件数量：{}", memorandumList.size());
    }

    @Override
    public List<MemorandumResponse> listByUserId(String sessionUserId) {
        return list(sessionUserId);
    }

    private void sendAll(List<MemorandumResponse> memorandums) throws IOException {
        String userId = StpUtil.getLoginIdAsString();
        CopyOnWriteArraySet<WebSocketSession> sessions = memorandumHandler.getSessions();
        for (WebSocketSession session : sessions) {
            if (WSUtil.getUserFromProtocols(session).getId().equals(userId)) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                        WSResponse.success("获取消息列表成功", MemorandumOperationType.REPLACE, memorandums))));
            }
        }
    }

    private void sendAllByUserIds(Set<String> userIdSet) throws IOException {
        CopyOnWriteArraySet<WebSocketSession> sessions = memorandumHandler.getSessions();
        for (WebSocketSession session : sessions) {
            String sessionUserId = WSUtil.getUserFromProtocols(session).getId();
            if (userIdSet.contains(sessionUserId)) {
                List<MemorandumResponse> memorandumResponseList = this.listByUserId(sessionUserId);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                        WSResponse.success("获取消息列表成功", MemorandumOperationType.REPLACE, memorandumResponseList))));
            }
        }
    }

}
