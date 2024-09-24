package com.hotstrip.flow.worker.service;

import javax.annotation.Resource;

import com.hotstrip.flow.worker.model.ExecRes;
import com.hotstrip.flow.worker.model.Node;
import org.junit.jupiter.api.Test;
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
  void run2() {
//    FlowHis flowHis = flowHisService.findById(1835606665463197696L);
    flowHisService.run(1835606665463197696L);
  }

  @Test
  void testRun() {
    FlowHis flowHis = flowHisService.findById(1835606665463197696L);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
    JSONObject jsonData = JSONUtil.parseObj(flowHis.getJsonData());
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
          ExecRes execRes = nodeService.run(node);
          node.getData().set("execRes", execRes);
          log.info("node exec res: {}", JSONUtil.toJsonStr(node));
        }
      });
//      Node node = nodes.stream().filter(n -> o.equals(n.getId())).findFirst().get();
//      if (node.getType().equals("shell")) {
//
//      }
    });
    // List<FlowHis> list = flowHisService.findByPage(1, 10, new FlowHis());
    // log.info("list: {}", JSONUtil.toJsonStr(list));
    // list.forEach(item -> {
    //   JSONObject json = JSONUtil.parseObj(item.getJsonData());
    //   log.info("id: {}...data: {}", item.getId(), json.toString());
    // });
  }
}
