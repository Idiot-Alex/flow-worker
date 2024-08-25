package com.hotstrip.flow.worker.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.model.Flow;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FLowServiceTest extends WorkerAppTest {

  @Resource
  private FLowService flowService;

  @Test
  void testDeleteById() {

  }

  @Test
  void testFindById() {
    Flow flow = flowService.findById(1L);
    log.info("flow: {}", JSONUtil.toJsonStr(flow));
  }

  @Test
  void testSave() {

  }

  @Test
  void testUpdate() {

  }
}
