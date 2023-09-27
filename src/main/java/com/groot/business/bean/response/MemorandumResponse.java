package com.groot.business.bean.response;

import com.groot.business.bean.enums.MemorandumContentType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MemorandumResponse {
    private String id;
    private String userId;
    private String content;
    private MemorandumContentType contentType;
    private Date createTime;
    private Date updateTime;
    private FileResponse file;
}
