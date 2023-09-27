package com.groot.business.bean.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FileResponse {
    private String id;
    private String originalName;
    private Date createTime;
    private Date updateTime;
    private boolean deleted;
}
