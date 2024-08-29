package com.hotstrip.flow.worker.env.git;

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
    return RuntimeUtil.execForStr("git --version");
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
