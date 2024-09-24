package com.hotstrip.flow.worker.model;

import java.util.Date;

import cn.hutool.json.JSONObject;
import com.hotstrip.flow.worker.typehandler.BlobToJsonObjectTypeHandler;
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
  @Entity.Column(value = "json_data", typeHandler = BlobToJsonObjectTypeHandler.class)
  private JSONObject jsonData;
  @Entity.Column(value = "created_at", updatable = false)
  private Date createdAt;
  @Entity.Column(value = "updated_at", insertable = false)
  private Date updatedAt;

  @Transient
  private String idStr;
  @Transient
  private Integer maxSeqNo;

  public String getIdStr() {
    return id + "";
  }

}
