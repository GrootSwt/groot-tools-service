package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groot.business.mapper.MemorandumMapper;
import com.groot.business.model.Memorandum;
import com.groot.business.service.MemorandumService;
import com.groot.business.ws.MemorandumHandler;

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
    public List<Memorandum> list() {
        QueryWrapper<Memorandum> wrapper = new QueryWrapper<>();
        String userId = StpUtil.getLoginIdAsString();
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
    public void deleteById(String id) throws IOException {
        Memorandum memorandum = new Memorandum();
        memorandum.setId(id);
        memorandumMapper.deleteById(memorandum);
        List<Memorandum> memorandums = this.list();
        MemorandumHandler.sendAll(memorandums);
    }
}
