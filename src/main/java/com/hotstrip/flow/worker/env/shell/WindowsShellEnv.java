package com.hotstrip.flow.worker.env.shell;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WindowsShellEnv implements EnvStrategy {
    @Override
    public String name() {
        return "shell";
    }

    @Override
    public String path() {
        String shellPath;
        try {
            shellPath = RuntimeUtil.execForStr("where powershell").trim();
        } catch (Exception e) {
            log.error("Error getting PowerShell path...message: {}", e.getMessage(), e);
            shellPath = null;
        }
        return shellPath;
    }

    @Override
    public String version() {
        String versionOutput;
        try {
            versionOutput = RuntimeUtil.execForStr("$PSVersionTable.PSVersion.ToString()").trim();
            return versionOutput;
        } catch (Exception e) {
            log.error("Error getting PowerShell version...message: {}", e.getMessage(), e);
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
