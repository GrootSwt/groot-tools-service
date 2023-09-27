package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groot.business.bean.entity.MemorandumAndFile;
import com.groot.business.bean.enums.MemorandumContentType;
import com.groot.business.bean.response.FileResponse;
import com.groot.business.bean.response.MemorandumResponse;
import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.mapper.FileMapper;
import com.groot.business.mapper.MemorandumMapper;
import com.groot.business.model.FileModel;
import com.groot.business.model.Memorandum;
import com.groot.business.service.FileService;
import com.groot.business.service.MemorandumService;

import cn.dev33.satoken.stp.StpUtil;

import com.groot.business.utils.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemorandumServiceImpl implements MemorandumService {

    private final MemorandumMapper memorandumMapper;

    private final FileService fileService;

    private final FileMapper fileMapper;

    public MemorandumServiceImpl(final MemorandumMapper memorandumMapper,
                                 final FileService fileService,
                                 final FileMapper fileMapper) {
        this.memorandumMapper = memorandumMapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
    }

    @Override
    public List<MemorandumResponse> list() {
        String userId = StpUtil.getLoginIdAsString();
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
                CommonUtil.deleteFile(Paths.get(fileModel.getLocationUrl()));
                fileMapper.deleteById(fileId);
            }
            memorandumMapper.deleteById(id);
        }
        List<MemorandumResponse> memorandums = this.list();
        com.groot.business.ws.Memorandum.sendAll(memorandums);
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
        List<MemorandumResponse> memorandums = this.list();
        com.groot.business.ws.Memorandum.sendAll(memorandums);
    }
}
