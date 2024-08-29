package com.hotstrip.flow.worker.env.java;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaEnvTest extends WorkerAppTest {

  @Resource
  private JavaEnv javaEnv;

  @Test
  void testInfo() {

  }

  @Test
  void testName() {

  }

  @Test
  void testPath() {
    String path = javaEnv.path();
    log.info("java path: {}", path);
  }

  @Test
  void testVersion() {
    String version = javaEnv.version();
    log.info("java version: {}", version);
  }
}
