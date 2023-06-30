package com.groot.business.bean.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
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
}
