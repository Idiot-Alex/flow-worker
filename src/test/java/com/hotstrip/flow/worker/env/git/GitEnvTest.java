package com.hotstrip.flow.worker.env.git;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import com.hostrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.env.git.GitEnv;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GitEnvTest extends WorkerAppTest {

  @Resource
  private GitEnv gitEnv;

  @Test
  void testInfo() {

  }

  @Test
  void testName() {

  }

  @Test
  void testPath() {
    String path = gitEnv.path();
    log.info("path: {}", path);

    Process process = RuntimeUtil.exec("which git");
    log.info(JSONUtil.toJsonStr(process));

    // Process process = RuntimeUtil.exec("which git");
  }

  @Test
  void testVersion() {

  }
}
