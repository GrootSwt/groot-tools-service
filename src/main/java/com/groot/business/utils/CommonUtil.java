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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Setter
public class CommonUtil {

    /**
     * @param fileRootPath     文件存储根路径
     * @param originalFilename 源文件名
     * @return 文件路径 示例：./files/2023-10-10/abc.jpeg
     * @throws IOException 文件路径生成异常
     */
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

    /**
     * 删除当前路径文件
     * 如果父文件夹为空的时候，同时删除父文件夹
     *
     * @param path 当前文件路径
     */
    public static void deleteFileAndParentDirIfEmpty(Path path) {
        deleteFile(path);
        deleteParentDirIfEmpty(path);
    }

    /**
     * 如果路径上文件存在，执行删除操作
     *
     * @param path 文件路径
     */
    public static void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("文件删除失败", e);
        }
    }

    /**
     * 路径的父路径为文件夹并且为空，执行删除父路径操作
     *
     * @param path 文件路径
     */
    public static void deleteParentDirIfEmpty(Path path) {
        Path parentPath = path.getParent();
        if (Files.exists(parentPath) && Files.isDirectory(parentPath)) {
            try (Stream<Path> entries = Files.list(parentPath)) {
                Set<Path> childrenPathSet = entries.collect(Collectors.toSet());
                if (childrenPathSet.isEmpty()) {
                    Files.deleteIfExists(parentPath);
                }
            } catch (IOException e) {
                log.error("父文件夹删除失败");
            }
        }
    }

    public static void downloadFile(String filename, Path path, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setCharacterEncoding("utf-8");
        try (OutputStream outputStream = response.getOutputStream()) {
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new BusinessRuntimeException("文件下载失败", 400);
        }
    }
}
