package com.hotstrip.flow.worker.env.java;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;

public class LinuxJavaEnv implements EnvStrategy {

  @Override
  public String name() {
    return "java";
  }

  @Override
  public String path() {
    return RuntimeUtil.execForStr("which java");
  }

  @Override
  public String version() {
    Process process = RuntimeUtil.exec("java -version");
    String res = RuntimeUtil.getErrorResult(process);
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
