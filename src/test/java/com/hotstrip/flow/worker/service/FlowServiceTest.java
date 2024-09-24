package com.hotstrip.flow.worker.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.hotstrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.mapper.FlowMapper;
import com.hotstrip.flow.worker.model.Flow;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowServiceTest extends WorkerAppTest {

  @Resource
  private FlowService flowService;
  @Resource
  private FlowMapper flowMapper;

  @Test
  void testDeleteById() {

  }

  @Test
  void testFindById() {
    List<Flow> flows = flowService.findByPage(1, 10, new Flow());
    log.info("flows: {}", JSONUtil.toJsonStr(flows));
  }

  @Test
  void testSave() {
    // 创建一个 Flow 对象
    Flow flow = new Flow();
    flow.setId(IdUtil.getSnowflake().nextId());
    flow.setName("testFlow");
    flow.setJsonData(new JSONObject());
    flow.setCreatedAt(new Date());

    int count = flowMapper.insert(flow);
    log.info("count: {}", count);
    // Flow res = flowService.save(flow);
    // log.info("res: {}", JSONUtil.toJsonStr(res));
  }

  @Test
  void testUpdate() {

  }
}
