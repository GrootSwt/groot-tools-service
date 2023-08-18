package com.groot.business.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private String id;

  private String account;

  private String displayName;

  private String phoneNumber;

  private String email;

}
