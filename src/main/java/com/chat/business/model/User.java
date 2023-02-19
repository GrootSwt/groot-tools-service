package com.chat.business.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

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
