package com.groot.business.service;

import com.groot.business.model.Memorandum;

import java.io.IOException;
import java.util.List;

public interface MemorandumService {

    List<Memorandum> list();
    void save(Memorandum memorandum);

    void deleteById(String id) throws IOException;
}
