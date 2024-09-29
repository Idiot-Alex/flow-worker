package com.hotstrip.flow.worker.service;

import javax.annotation.Resource;

import com.hotstrip.flow.worker.model.ExecRes;
import com.hotstrip.flow.worker.model.Node;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import com.hotstrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.model.FlowHis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowHisServiceTest extends WorkerAppTest {
  @Resource
  private FlowService flowService;
  @Resource
  private FlowHisService flowHisService;
  @Resource
  private NodeService nodeService;

  @Test
  void testInitFlowHis() {
    Flow flow = flowService.findById(1835258299420094464L);
    FlowHis flowHis = flowHisService.initFlowHis(flow);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
  }

  @Test
  void testFindByPage() {
    List<FlowHis> list = flowHisService.findByPage(0, 10, new FlowHis());
    log.info("list: {}", JSONUtil.toJsonStr(list));
  }

  @Test
  void testRun1() {
    JSONObject data = new JSONObject();
    data.putIfAbsent("cmd", "-v");

    Node node = new Node();
    node.setId(System.currentTimeMillis());
    node.setType("maven");
    node.setData(data);

    nodeService.run(node);
  }

  @Test
  void testUpdateNode() {
    Node node = nodeService.findById(1727430246263L);
    log.info("node: {}", JSONUtil.toJsonStr(node));
    node.setUpdatedAt(new Date());
    nodeService.updateById(node);
  }

  @Test
  void run2() {
    long flowHisId = 1839602008181239808L;
    FlowHis flowHis = flowHisService.findById(flowHisId);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
    flowHisService.run(flowHisId);
    flowHis = flowHisService.findById(flowHisId);
    log.info("executed flowHis: {}", JSONUtil.toJsonStr(flowHis));
  }

  @Test
  void testRunWindows() {
    long flowHisId = 1840207964489494528L;
    FlowHis flowHis = flowHisService.findById(flowHisId);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
    flowHisService.run(flowHisId);
    flowHis = flowHisService.findById(flowHisId);
    log.info("executed flowHis: {}", JSONUtil.toJsonStr(flowHis));
  }

  @Test
  void testRun() {
    FlowHis flowHis = flowHisService.findById(1835606665463197696L);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
    JSONObject jsonData = flowHis.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    log.info(JSONUtil.toJsonStr(nodes));

    JSONObject sort = jsonData.getJSONObject("sort");
    log.info(sort.toJSONString(2));
    List<String> order = sort.getBeanList("order", String.class);
    log.info("order: {}", order);

    JSONObject predecessors = sort.getJSONObject("predecessors");
    log.info("predecessors: {}", predecessors.toJSONString(2));

    order.forEach(o -> {
      List<String> list = predecessors.getBeanList(o, String.class);
      log.info("order: {}...list: {}", o, JSONUtil.toJsonStr(list));
      nodes.forEach(node -> {
        if (!"start".equals(node.getType())) {
          log.info(JSONUtil.toJsonStr(node));
          Node executedNode = nodeService.run(node);
          log.info("node exec res: {}", JSONUtil.toJsonStr(executedNode));
        }
      });
    });
  }
}
