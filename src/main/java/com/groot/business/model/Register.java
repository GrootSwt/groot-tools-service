package com.groot.business.model;

import com.groot.business.bean.enums.RegisterStatus;
import com.groot.business.model.base.BaseModel;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Register extends BaseModel {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String displayName;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 注册状态
     */
    private RegisterStatus status;
}
