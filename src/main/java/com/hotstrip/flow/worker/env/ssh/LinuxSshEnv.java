package com.hotstrip.flow.worker.env.ssh;

import cn.hutool.core.util.RuntimeUtil;
import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxSshEnv implements EnvStrategy {
    @Override
    public String name() {
        return "ssh";
    }

    @Override
    public String path() {
        return RuntimeUtil.execForStr("which ssh").trim();
    }

    @Override
    public String version() {
        return RuntimeUtil.execForStr("ssh -V");
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
