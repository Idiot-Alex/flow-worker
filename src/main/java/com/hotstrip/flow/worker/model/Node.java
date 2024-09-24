package com.hotstrip.flow.worker.model;

import cn.hutool.json.JSONObject;
import com.hotstrip.flow.worker.typehandler.BlobToJsonObjectTypeHandler;
import com.hotstrip.flow.worker.typehandler.BlobToStringTypeHandler;
import io.mybatis.provider.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity.Table(value = "node")
public class Node {
    @Entity.Column(value = "id", id = true)
    private Long id;
    @Entity.Column(value = "flow_his_id")
    private Long flowHisId;
    @Entity.Column(value = "seq_no")
    private Integer seqNo;
    @Entity.Column(value = "type")
    private String type;
    @Entity.Column(value = "data", typeHandler = BlobToJsonObjectTypeHandler.class)
    private JSONObject data;
    @Entity.Column(value = "created_at", updatable = false)
    private Date createdAt;
    @Entity.Column(value = "updated_at", insertable = false)
    private Date updatedAt;
}
