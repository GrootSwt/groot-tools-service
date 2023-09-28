package com.groot.business.service;

import com.groot.business.bean.response.MemorandumResponse;
import com.groot.business.model.Memorandum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemorandumService {

    List<MemorandumResponse> list(String userId);
    void save(Memorandum memorandum);

    void deleteById(String id) throws IOException;

    void uploadFile(MultipartFile file) throws IOException;

    void autoDeleteOutdatedFileTypeMemorandum() throws IOException;

    List<MemorandumResponse> listByUserId(String sessionUserId);
}
