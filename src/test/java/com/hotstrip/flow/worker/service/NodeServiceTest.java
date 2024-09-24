package com.hotstrip.flow.worker.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
    void testSave() {
        JSONObject jsonObject = new JSONObject();
        Node node = new Node();
        node.setId(System.currentTimeMillis());
        node.setSeqNo(0);
        node.setType("start");
        node.setFlowHisId(1L);
        node.setData(jsonObject);
        nodeService.save(node);
    }

    @Test
    void testQuery() {
        Node node = nodeService.findById(1727161100148L);
        log.info("node: {}", JSONUtil.toJsonStr(node));
    }

    @Test
    void run() {
        JSONObject data = new JSONObject();
        data.putIfAbsent("cmd", "-c ls -l");

        Node node = new Node();
        node.setId(System.currentTimeMillis());
        node.setType("shell");
        node.setData(data);

        nodeService.run(node);
    }
}