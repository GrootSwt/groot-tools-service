package com.groot.base.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUser {

    /**
     * 当前登陆用户id
     */
    private String userId;

}
