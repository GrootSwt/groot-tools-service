package com.chat.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chat.business.mapper.MemorandumMapper;
import com.chat.business.model.Memorandum;
import com.chat.business.service.MemorandumService;
import com.chat.business.ws.MemorandumHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class MemorandumServiceImpl implements MemorandumService {

    private final MemorandumMapper memorandumMapper;

    @Autowired
    public MemorandumServiceImpl(MemorandumMapper memorandumMapper) {
        this.memorandumMapper = memorandumMapper;
    }


    @Override
    public List<Memorandum> listByUserId(String userId) {
        QueryWrapper<Memorandum> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("create_time");
        return memorandumMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Memorandum memorandum) {
        memorandumMapper.insert(memorandum);
    }

    @Override
    public void deleteMessageById(String id, String userId) throws IOException {
        Memorandum memorandum = new Memorandum();
        memorandum.setId(id);
        memorandumMapper.deleteById(memorandum);
        List<Memorandum> memorandums = this.listByUserId(userId);
        MemorandumHandler.sendAll(userId, memorandums);
    }
}
