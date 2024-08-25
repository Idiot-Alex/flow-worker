package com.hotstrip.flow.worker.model;

import java.util.Date;

import com.hotstrip.flow.worker.typehandler.BlobToStringTypeHandler;

import io.mybatis.provider.Entity;
import lombok.Data;

@Data
@Entity.Table(value = "flow")
public class Flow {
  @Entity.Column(id = true)
  private Long id;
  private String name;
  @Entity.Column(typeHandler = BlobToStringTypeHandler.class)
  private String jsonData;
  private Date createdAt;
  private Date updatedAt;
}
