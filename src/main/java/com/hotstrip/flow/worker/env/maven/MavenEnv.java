package com.hotstrip.flow.worker.env.maven;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;
import com.hotstrip.flow.worker.env.java.LinuxJavaEnv;
import com.hotstrip.flow.worker.env.java.WindowsJavaEnv;

import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MavenEnv implements EnvStrategy {

  private EnvStrategy strategy;

  public MavenEnv() {
    String osName = SystemUtil.getOsInfo().getName();
    if (osName.contains("Windows")) {
      strategy = new WindowsMavenEnv();
    } else if (osName.contains("Mac")) {
      strategy = new LinuxMavenEnv();
    } else if (osName.contains("Linux")) {
      strategy = new LinuxMavenEnv();
    } else {
      log.error("load maven env failed...Unsupported operating system: {}", osName);
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
