package com.hotstrip.flow.worker.env.shell;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LinuxShellEnv implements EnvStrategy {
    @Override
    public String name() {
        return "shell";
    }

    @Override
    public String path() {
        String shellPath;
        try {
            shellPath = RuntimeUtil.execForStr("echo $SHELL").trim();
        } catch (Exception e) {
            log.error("Error getting shell path...message: {}", e.getMessage(), e);
            shellPath = null;
        }
        return shellPath;
    }

    @Override
    public String version() {
        String versionOutput;
        try {
            versionOutput = RuntimeUtil.execForStr(this.path() + " --version").trim();
            Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");
            Matcher matcher = pattern.matcher(versionOutput);
            if (matcher.find()) {
                return matcher.group(1);
            } else {
                log.error("Failed to get shell version.");
                return null;
            }
        } catch (Exception e) {
            log.error("Error getting shell version...message: {}", e.getMessage(), e);
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
