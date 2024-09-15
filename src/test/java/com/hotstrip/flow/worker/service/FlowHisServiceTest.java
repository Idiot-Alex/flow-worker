package com.hotstrip.flow.worker.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.model.FlowHis;

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
}
