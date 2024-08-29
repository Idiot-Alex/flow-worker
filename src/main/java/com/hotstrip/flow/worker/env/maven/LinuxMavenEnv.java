package com.hotstrip.flow.worker.env.maven;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LinuxMavenEnv implements EnvStrategy {

  @Override
  public String name() {
    return "maven";
  }

  @Override
  public String path() {
    return RuntimeUtil.execForStr("which mvn");
  }

  @Override
  public String version() {
    String versionOutput = RuntimeUtil.execForStr("mvn -v");
    Pattern pattern = Pattern.compile("Apache Maven (\\S+)");
    Matcher matcher = pattern.matcher(versionOutput);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      log.error("Failed to get Maven version.");
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
