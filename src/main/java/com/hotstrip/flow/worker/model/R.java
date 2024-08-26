package com.hotstrip.flow.worker.model;

import lombok.Data;

/**
 * response model
 */
@Data
public class R {
  private String code;
  private String msg;
  private Object data;

  public static R init(String code, String msg, Object data) {
    R r = new R();
    r.setCode(code);
    r.setMsg(msg);
    r.setData(data);
    return r;
  }

  public static R ok(String msg, Object data) {
    return init("ok", msg, data);
  }

  public static R ok(Object data) {
    return init("ok", "success", data);
  }

  public static R error(String msg, Object data) {
    return init("error", msg, data);
  }
  
}
