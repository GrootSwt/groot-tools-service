package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.bean.entity.MemorandumAndFile;
import com.groot.business.model.Memorandum;

import java.util.List;

public interface MemorandumMapper extends BaseMapper<Memorandum> {

    List<MemorandumAndFile> listByUserId(String userId);
}
