package com.groot.business.bean.request.base;

import lombok.Data;

@Data
public class WSRequest<O, P> {

  private O operationType;

  private P params;
  
}
