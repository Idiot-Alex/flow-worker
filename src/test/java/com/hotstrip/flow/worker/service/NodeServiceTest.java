package com.hotstrip.flow.worker.service;

import cn.hutool.json.JSONObject;
import com.hotstrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

@Slf4j
public class NodeServiceTest extends WorkerAppTest {

    @Resource
    private NodeService nodeService;

    @Test
    void run() {
        JSONObject data = new JSONObject();
        data.putIfAbsent("cmd", "-c ls -l");

        Node node = new Node();
        node.setId(System.currentTimeMillis()+"");
        node.setType("shell");
        node.setData(data);

        nodeService.run(node);
    }
}