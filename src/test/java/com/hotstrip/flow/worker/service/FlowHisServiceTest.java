package com.hotstrip.flow.worker.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import java.util.List;

import com.hostrip.flow.worker.WorkerAppTest;
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

  @Test
  void testInitFlowHis() {
    Flow flow = flowService.findById(1835258299420094464L);
    FlowHis flowHis = flowHisService.initFlowHis(flow);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
  }

  @Test
  void testRun() {
    FlowHis flowHis = flowHisService.findById(1835606665463197696L);
    log.info("flowHis: {}", JSONUtil.toJsonStr(flowHis));
    JSONObject jsonData = JSONUtil.parseObj(item.getJsonData());
    log.info(jsonData.get("nodes"));
    // List<FlowHis> list = flowHisService.findByPage(1, 10, new FlowHis());
    // log.info("list: {}", JSONUtil.toJsonStr(list));
    // list.forEach(item -> {
    //   JSONObject json = JSONUtil.parseObj(item.getJsonData());
    //   log.info("id: {}...data: {}", item.getId(), json.toString());
    // });
  }
}
