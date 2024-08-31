package com.hotstrip.flow.worker.model;

import java.util.HashMap;

import com.github.pagehelper.Page;

/** 
 * response for map
 */
public class RMap extends HashMap<String, Object> {

  public static RMap init(String code, String msg, Object data) {
    RMap r = new RMap();
    r.put("code", code);
    r.put("msg", msg);
    r.put("data", data);
    return r;
  }

  public static RMap ok(String msg, Object data) {
    return init("ok", msg, data);
  }

  public static RMap ok(String msg) {
    return init("ok", msg, null);
  }

  public static RMap ok(Object data) {
    return init("ok", "success", data);
  }

  public static RMap error(String msg) {
    return init("error", msg, null);
  }

  public static RMap error(String msg, Object data) {
    return init("error", msg, data);
  }

  public RMap initPage(Page<?> page) {
    this.put("page", page.getPageNum());
    this.put("size", page.getPageSize());
    this.put("total", page.getTotal());
    return this;
  }

}
