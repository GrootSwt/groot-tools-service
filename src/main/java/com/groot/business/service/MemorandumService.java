package com.groot.business.service;

import com.groot.business.model.Memorandum;

import java.io.IOException;
import java.util.List;

public interface MemorandumService {

    List<Memorandum> listByUserId(String userId);
    void save(Memorandum memorandum);

    void deleteMessageById(String id, String userId) throws IOException;
}
