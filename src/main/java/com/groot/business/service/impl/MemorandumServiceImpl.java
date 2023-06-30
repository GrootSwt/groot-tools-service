package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groot.business.mapper.MemorandumMapper;
import com.groot.business.service.MemorandumService;
import com.groot.business.ws.Memorandum;

import cn.dev33.satoken.stp.StpUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class MemorandumServiceImpl implements MemorandumService {

    private final MemorandumMapper memorandumMapper;

    public MemorandumServiceImpl(MemorandumMapper memorandumMapper) {
        this.memorandumMapper = memorandumMapper;
    }

    @Override
    public List<com.groot.business.model.Memorandum> list() {
        QueryWrapper<com.groot.business.model.Memorandum> wrapper = new QueryWrapper<>();
        String userId = StpUtil.getLoginIdAsString();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("create_time");
        return memorandumMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(com.groot.business.model.Memorandum memorandum) {
        memorandumMapper.insert(memorandum);
    }

    @Override
    public void deleteById(String id) throws IOException {
        com.groot.business.model.Memorandum memorandum = new com.groot.business.model.Memorandum();
        memorandum.setId(id);
        memorandumMapper.deleteById(memorandum);
        List<com.groot.business.model.Memorandum> memorandums = this.list();
        Memorandum.sendAll(memorandums);
    }
}
