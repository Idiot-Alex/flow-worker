package com.hotstrip.flow.worker.env.ssh;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvStrategy;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WindowsSshEnv implements EnvStrategy {
    @Override
    public String name() {
        return "ssh";
    }

    @Override
    public String path() {
        List<String> res = RuntimeUtil.execForLines("where ssh");
        log.info("get ssh path: {}", res);
        // 可能会有多个 ssh 路径，取第一个
        if (res != null && res.size() > 0) {
            return res.get(0).trim();
        } else {
            return null;
        }
    }

    @Override
    public String version() {
        String versionOutput;
        try {
            versionOutput = RuntimeUtil.execForStr(this.path(), "-V 2>&1").trim();
            Pattern pattern = Pattern.compile("(OpenSSH_\\w+\\d+\\.\\d+(p\\d+)?)");
            Matcher matcher = pattern.matcher(versionOutput);
            if (matcher.find()) {
                return matcher.group(1);
            } else {
                log.error("Failed to get ssh version.");
                return null;
            }
        } catch (Exception e) {
            log.error("Error getting ssh version...message: {}", e.getMessage(), e);
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
