package com.hotstrip.flow.worker.env.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    return RuntimeUtil.execForStr("which java").trim();
  }

  @Override
  public String version() {
    String versionOutput = RuntimeUtil.execForStr("java -version");
    Pattern pattern = Pattern.compile("version \"([^\"]+)\"");
    Matcher matcher = pattern.matcher(versionOutput);
    if (matcher.find()) {
      return matcher.group(1);
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
