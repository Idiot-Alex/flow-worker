package com.hotstrip.flow.worker.env.shell;

import javax.annotation.Resource;

import cn.hutool.json.JSONUtil;
import com.hotstrip.flow.worker.env.Env;
import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.env.EnvManager;
import com.hotstrip.flow.worker.env.EnvStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShellEnvTest extends WorkerAppTest {

  @Resource
  private EnvManager envManager;
  @Resource
  private ShellEnv shellEnv;

  @Test
  void testInfo() {
    Env env = shellEnv.info();
    log.info("env: {}", JSONUtil.toJsonStr(env));
  }

  @Test
  void testName() {
    log.info("env name: {}", shellEnv.name());
  }

  @Test
  void testPath() {
    EnvStrategy env = envManager.getEnv("shell");
    log.info(env.path());
  }

  @Test
  void testVersion() {
    log.info("env version: {}", shellEnv.version());
  }
}
