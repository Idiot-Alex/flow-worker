package com.hotstrip.flow.worker.env.maven;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MavenEnvTest extends WorkerAppTest {

  @Resource
  private MavenEnv mavenEnv;

  @Test
  void testInfo() {
    log.info("maven info: {}", mavenEnv.info());
  }

  @Test
  void testName() {

  }

  @Test
  void testPath() {
    log.info("maven path: {}", mavenEnv.path());
  }

  @Test
  void testVersion() {

  }
}
