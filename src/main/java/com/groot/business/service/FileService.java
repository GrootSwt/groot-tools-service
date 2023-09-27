package com.groot.business.service;

import com.groot.business.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public FileModel upload(MultipartFile file);

    FileModel selectById(String id);
}
