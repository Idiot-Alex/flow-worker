package com.hotstrip.flow.worker.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvManager;
import com.hotstrip.flow.worker.env.EnvStrategy;
import com.hotstrip.flow.worker.model.Node;
import com.hotstrip.flow.worker.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Slf4j
@Service
public class NodeServiceImpl implements NodeService {

    @Resource
    private EnvManager envManager;
    @Override
    public void run(Node node) {
        Assert.notNull(node, "node cannot be null");
        Assert.notNull(node.getData(), "node data cannot be null");

        JSONObject data = node.getData();
        String nodeType = node.getType();
        String cmd = data.getStr("cmd");
        if (StrUtil.isBlank(cmd)) {
            log.error("the cmd is blank at node id: {}, type: {}", node.getId(), nodeType);
            throw new RuntimeException(String.format("the cmd is blank at node id: %s, type: %s", node.getId(), nodeType));
        }
        EnvStrategy envStrategy = envManager.getEnv(nodeType);
        if (null == envStrategy) {
            log.error("there is no env named: {}", nodeType);
            throw new RuntimeException("no env named: " + nodeType);
        }

        String[] cmdArgs = cmd.split("\\s+");
        log.info("cmdArgs: {}", cmdArgs);

        Env env = envStrategy.info();
        log.info("env: {}", JSONUtil.toJsonStr(env));

        String[] execCmd = ArrayUtil.insert(cmdArgs, 0, env.getPath());
        log.info("execCmd: {}", execCmd);
        String output = RuntimeUtil.execForStr(execCmd);
        log.info(output);
    }
}
