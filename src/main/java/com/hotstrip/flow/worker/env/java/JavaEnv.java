package com.hotstrip.flow.worker.env.java;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JavaEnv implements EnvStrategy {

  private EnvStrategy strategy;

  public JavaEnv() {
    String osName = SystemUtil.getOsInfo().getName();
    if (osName.contains("Windows")) {
      strategy = new WindowsJavaEnv();
    } else if (osName.contains("Mac")) {
      strategy = new LinuxJavaEnv();
    } else if (osName.contains("Linux")) {
      strategy = new LinuxJavaEnv();
    } else {
      log.error("load java env failed...Unsupported operating system: {}", osName);
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
