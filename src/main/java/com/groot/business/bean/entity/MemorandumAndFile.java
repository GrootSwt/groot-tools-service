package com.groot.business.bean.entity;

import com.groot.business.bean.enums.MemorandumContentType;
import lombok.Data;

import java.util.Date;

@Data
public class MemorandumAndFile {
    private String id;
    private String userId;
    private String content;
    private MemorandumContentType contentType;
    private Date createTime;
    private Date updateTime;
    private String fileId;
    private String fileOriginalName;
    private Date fileCreateTime;
    private Date fileUpdateTime;
    private boolean fileDeleted;
}
