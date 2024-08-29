package com.hotstrip.flow.worker.env.java;

import java.util.List;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WindowsJavaEnv implements EnvStrategy {

  @Override
  public String name() {
    return "java";
  }

  @Override
  public String path() {
    List<String> res = RuntimeUtil.execForLines("where java");
    log.info("get java path: {}", res);
    // 可能会有多个java路径，取第一个
    if (res != null && res.size() > 0) {
      return res.get(0).trim();
    } else {
      return null;
    }
  }

  @Override
  public String version() {
    String res = RuntimeUtil.execForStr("java -version");
    log.info("get java version: {}", res);
    String versionMatch = res.replaceAll("\"", "").split("version")[1].trim();
    if (versionMatch != null && !versionMatch.isEmpty()) {
      return versionMatch;
    } else {
      System.err.println("Failed to get JDK version.");
      return null;
    }
  }

  @Override
  public Env info() {
    return Env.builder()
      .name(this.name())
      .path(this.path())
      .version(this.version())
      .build();
  }

}
