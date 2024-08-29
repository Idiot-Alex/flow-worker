package com.hotstrip.flow.worker.env.java;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String versionOutput = RuntimeUtil.execForStr("java -version");
    log.info("get java version: {}", versionOutput);
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
