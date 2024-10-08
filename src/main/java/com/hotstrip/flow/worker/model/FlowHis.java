package com.hotstrip.flow.worker.model;

import java.util.Date;

import cn.hutool.json.JSONObject;
import com.hotstrip.flow.worker.typehandler.BlobToJsonObjectTypeHandler;
import io.mybatis.provider.Entity;
import io.mybatis.provider.Entity.Transient;
import lombok.Data;

@Data
@Entity.Table(value = "flow_his")
public class FlowHis {
  @Entity.Column(value = "id", id = true)
  private Long id;
  @Entity.Column(value = "flow_id")
  private Long flowId;
  @Entity.Column(value = "seq_no")
  private Integer seqNo;
  @Entity.Column(value = "json_data", typeHandler = BlobToJsonObjectTypeHandler.class)
  private JSONObject jsonData;
  @Entity.Column(value = "start_at")
  private Date startAt;
  @Entity.Column(value = "end_at")
  private Date endAt;
  @Entity.Column(value = "created_at", updatable = false)
  private Date createdAt;
  @Entity.Column(value = "updated_at", insertable = false)
  private Date updatedAt;

  @Transient
  private String idStr;
  @Transient
  private String flowIdStr;

  public String getIdStr() {
    return id + "";
  }

  public String getFlowIdStr() {
    return flowId + "";
  }

}
