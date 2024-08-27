package com.hotstrip.flow.worker.api;

import java.util.Date;
import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hostrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.model.Flow;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowControllerTest extends WorkerAppTest {

  @Resource
  private TestRestTemplate restTemplate;

  @Test
  public void testSaveFlow() {
    // 创建一个 Flow 对象
    Flow flow = new Flow();
    flow.setId(IdUtil.getSnowflake().nextId());
    flow.setName("testFlow");
    flow.setJsonData("{\"key\":\"value\"}");
    flow.setCreatedAt(new Date());

    // 发送 POST 请求到 /api/flow/save
    ResponseEntity<String> response = restTemplate.postForEntity("/api/flow/save", flow, String.class);

    // 验证响应状态码是否为 200 OK
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // 验证响应体是否为 "Flow saved successfully"
    Assertions.assertEquals("Flow saved successfully", response.getBody());
  }

  @Test
  public void testListFlow() {
    Flow flow = new Flow();
    flow.setName("testFlow");

    // 发送 POST 请求到 /api/flow/list
    ResponseEntity<String> response = restTemplate.postForEntity("/api/flow/list?page=1&size=10", flow, String.class);
    log.info("response: {}", response);
  }
}
