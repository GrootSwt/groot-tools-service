package com.groot.business.utils;

import com.groot.business.exception.BusinessRuntimeException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public class CommonUtil {

    public static Path generateFilepath(String fileRootPath, String originalFilename) throws IOException {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String localFilename = UUID.randomUUID() + extension;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String parentDir = year + "-" + (month >= 10 ? month : "0" + month) + "-" + (day >= 10 ? day : "0" + day);
        Path rootPath = Paths.get(fileRootPath, parentDir).toAbsolutePath().normalize();
        if (Files.notExists(rootPath)) {
            Files.createDirectories(rootPath);
        }
        return rootPath.resolve(localFilename).normalize();
    }

    public static void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("文件删除失败", e);
        }
    }

    public static void downloadFile(String filename, Path path, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setCharacterEncoding("utf-8");
        try(OutputStream outputStream = response.getOutputStream()) {
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new BusinessRuntimeException("文件下载失败", 400);
        }
    }
}
