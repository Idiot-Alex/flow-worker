package com.hotstrip.flow.worker.model;

import java.util.Date;

import com.hotstrip.flow.worker.typehandler.BlobToStringTypeHandler;

import io.mybatis.provider.Entity;
import io.mybatis.provider.Entity.Transient;
import lombok.Data;

@Data
@Entity.Table(value = "flow")
public class Flow {
  @Entity.Column(value = "id", id = true)
  private Long id;
  @Entity.Column(value = "name")
  private String name;
  @Entity.Column(value = "json_data", typeHandler = BlobToStringTypeHandler.class)
  private String jsonData;
  @Entity.Column(value = "created_at", updatable = false)
  private Date createdAt;
  @Entity.Column(value = "updated_at", insertable = false)
  private Date updatedAt;

  @Transient
  private String idStr;

  public String getIdStr() {
    return id + "";
  }

}
