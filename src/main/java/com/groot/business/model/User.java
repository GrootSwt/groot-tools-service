package com.groot.business.model;

import com.groot.business.model.base.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseModel {
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
}
