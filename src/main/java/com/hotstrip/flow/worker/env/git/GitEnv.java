package com.hotstrip.flow.worker.env.git;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GitEnv implements EnvStrategy {

  private EnvStrategy strategy;

  public GitEnv() {
    String osName = SystemUtil.getOsInfo().getName();
    if (osName.contains("Windows")) {
      strategy = new WindowsGitEnv();
    } else if (osName.contains("Mac")) {
      strategy = new LinuxGitEnv();
    } else if (osName.contains("Linux")) {
      strategy = new LinuxGitEnv();
    } else {
      log.error("load git env failed...Unsupported operating system: {}", osName);
    }
  }

  @Override
  public String name() {
    return strategy == null ? null : strategy.name();
  }

  @Override
  public String path() {
    return strategy == null ? null : strategy.path();
  }

  @Override
  public String version() {
    return strategy == null ? null : strategy.version();
  }

  @Override
  public Env info() {
    return strategy == null ? null : strategy.info();
  }

}
