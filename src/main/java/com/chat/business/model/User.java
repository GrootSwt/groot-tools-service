package com.chat.business.model;

import com.chat.base.model.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseModel {

    @NotBlank(message = "用户名不可为空")
    @Length(min = 3, max = 20, message = "用户名长度位于3-20之间")
    private String username;

    @NotBlank(message = "密码不可为空")
    @Length(min = 6, max = 20, message = "密码长度位于6-20之间")
    private String password;

    @NotBlank(message = "系统密码不可为空")
    @Length(min = 6, max = 20, message = "系统密码长度位于6-20之间")
    private String systemPassword;
}
