package com.hotstrip.flow.worker.env.ssh;

import cn.hutool.json.JSONUtil;
import com.hotstrip.flow.worker.WorkerAppTest;
import com.hotstrip.flow.worker.env.Env;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class SshEnvTest extends WorkerAppTest {
    @Resource
    private SshEnv sshEnv;

    @Test
    void info() {
        Env info = sshEnv.info();
        log.info("info: {}", JSONUtil.toJsonStr(info));
    }
}