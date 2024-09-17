package com.hotstrip.flow.worker.env.maven;

import javax.annotation.Resource;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvManager;
import com.hotstrip.flow.worker.env.EnvStrategy;
import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MavenEnvTest extends WorkerAppTest {

  @Resource
  private MavenEnv mavenEnv;
  @Resource
  private EnvManager envManager;

  @Test
  void testInfo() {
    log.info("maven info: {}", mavenEnv.info());
  }

  @Test
  void testName() {
    EnvStrategy env = envManager.getEnv("maven");
    log.info(env.path());
  }

  @Test
  void testPath() {
    log.info("maven path: {}", mavenEnv.path());
  }

  @Test
  void testVersion() {

  }
}
