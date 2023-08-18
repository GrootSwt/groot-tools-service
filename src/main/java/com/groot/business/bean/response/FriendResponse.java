package com.groot.business.bean.response;

import lombok.Data;

@Data
public class FriendResponse {
    private String id;
    /**
     * 当前用户id
     */
    private String userId;
    /**
     * 关联用户id
     */
    private String friendId;
    /**
     * 关联用户账户
     */
    private String account;
    /**
     * 关联用户昵称
     */
    private String displayName;
    /**
     * 关联用户电话号码
     */
    private String phoneNumber;
    /**
     * 关联用户备注名
     */
    private String commentName;
}
