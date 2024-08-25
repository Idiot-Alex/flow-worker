package com.hotstrip.flow.worker.model;

import java.util.Date;

import com.hotstrip.flow.worker.typehandler.BlobToStringTypeHandler;

import io.mybatis.provider.Entity;
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
  @Entity.Column(value = "created_at")
  private Date createdAt;
  @Entity.Column(value = "updated_at")
  private Date updatedAt;
}
