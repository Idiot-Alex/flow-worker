package com.hotstrip.flow.worker.model;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class Node {
    private String id;
    private String type;
    private JSONObject data;
}
