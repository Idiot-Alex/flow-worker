package com.hotstrip.flow.worker.env.ssh;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

public class WindowsSshEnv implements EnvStrategy {
    @Override
    public String name() {
        return "ssh";
    }

    @Override
    public String path() {
        return null;
    }

    @Override
    public String version() {
        return null;
    }

    @Override
    public Env info() {
        return null;
    }
}
