package com.groot.business.bean.request.base;

import lombok.Data;

@Data
public class WSRequest<P> {

  private String operationType;

  private P params;
  
}
