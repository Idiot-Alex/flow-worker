package com.hotstrip.flow.worker.env.ssh;

import cn.hutool.system.SystemUtil;
import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;
import com.hotstrip.flow.worker.env.maven.LinuxMavenEnv;
import com.hotstrip.flow.worker.env.maven.WindowsMavenEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SshEnv implements EnvStrategy {

    private EnvStrategy strategy;

    public SshEnv() {
        String osName = SystemUtil.getOsInfo().getName();
        if (osName.contains("Windows")) {
            strategy = new WindowsSshEnv();
        } else if (osName.contains("Mac")) {
            strategy = new LinuxSshEnv();
        } else if (osName.contains("Linux")) {
            strategy = new LinuxSshEnv();
        } else {
            log.error("load ssh env failed...Unsupported operating system: {}", osName);
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
