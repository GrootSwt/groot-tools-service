package com.groot.business.bean.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountExistCheckResponse {
    /**
     * 账户是否存在
     */
    private Boolean accountExist;
}
