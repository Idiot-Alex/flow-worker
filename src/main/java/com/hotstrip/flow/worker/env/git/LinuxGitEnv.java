package com.hotstrip.flow.worker.env.git;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;

public class LinuxGitEnv implements EnvStrategy {

  @Override
  public String name() {
    return "git";
  }

  @Override
  public String path() {
    return RuntimeUtil.execForStr("which git").trim();
  }

  @Override
  public String version() {
    String versionOutput = RuntimeUtil.execForStr("git --version");
    Pattern pattern = Pattern.compile("git version (\\S+)");
    Matcher matcher = pattern.matcher(versionOutput);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      System.err.println("Failed to get git version.");
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
