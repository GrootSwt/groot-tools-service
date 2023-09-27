package com.groot.business.service.impl;

import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.mapper.FileMapper;
import com.groot.business.model.FileModel;
import com.groot.business.service.FileService;
import com.groot.business.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value("${application.file-root-path}")
    private String fileRootPath;

    private final FileMapper fileMapper;

    public FileServiceImpl(final FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileModel upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        Path path = null;
        try {
            assert originalFilename != null;
            path = CommonUtil.generateFilepath(fileRootPath, originalFilename);
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            if (null != path) {
                CommonUtil.deleteFile(path);
            }
            throw new BusinessRuntimeException("文件上传失败");
        }
        FileModel fileModel = FileModel.builder().originalName(originalFilename).locationUrl(path.toString()).build();
        fileMapper.insert(fileModel);
        return fileModel;
    }

    @Override
    public FileModel selectById(String id) {
        FileModel fileModel = fileMapper.selectById(id);
        if (null == fileModel) {
            throw new BusinessRuntimeException("文件不存在", 404);
        }
        return fileModel;
    }
}
