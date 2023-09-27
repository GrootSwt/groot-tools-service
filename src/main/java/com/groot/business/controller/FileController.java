package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;
import com.groot.business.model.FileModel;
import com.groot.business.service.FileService;
import com.groot.business.utils.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@RestController
@RequestMapping("file")
public class FileController {

    private final FileService fileService;

    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public Response<FileModel> upload(MultipartFile file) {
        FileModel fileModel = fileService.upload(file);
        return Response.success("path", fileModel);
    }

    @GetMapping("{id}/download")
    public void download(@PathVariable String id, HttpServletResponse response) {
        FileModel fileModel = fileService.selectById(id);
        CommonUtil.downloadFile(fileModel.getOriginalName(), Paths.get(fileModel.getLocationUrl()), response);
    }

}
