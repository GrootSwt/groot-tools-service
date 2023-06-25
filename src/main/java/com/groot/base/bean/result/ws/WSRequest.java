package com.groot.base.bean.result.ws;

import lombok.Data;

@Data
public class WSRequest<O, P> {

  private O operationType;

  private P params;
  
}
