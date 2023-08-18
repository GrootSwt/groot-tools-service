package com.groot.business.bean.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequest {
    /**
     * 账号
     */
    @NotBlank(message = "账号不可为空")
    @Length(min = 2, max = 20, message = "账号长度位于2-20之间")
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = "密码不可为空")
    @Length(min = 6, max = 20, message = "密码长度位于6-20之间")
    private String password;
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不可为空")
    @Length(min = 2, max = 10, message = "昵称长度位于2-10之间")
    private String displayName;
    /**
     * 电话号码
     */
    @Length(min = 5, max = 20, message = "电话号码长度位于5-20之间")
    private String phoneNumber;
    /**
     * 邮箱账号
     */
    @Length(min = 5, max = 30, message = "邮箱账号长度位于5-30之间")
    @Email
    private String email;
}
