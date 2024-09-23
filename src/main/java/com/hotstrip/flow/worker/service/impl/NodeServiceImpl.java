package com.hotstrip.flow.worker.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.mybatis.common.core.Code;
import io.mybatis.common.util.Assert;
import io.mybatis.service.AbstractService;

import com.hotstrip.flow.worker.env.Env;
import com.hotstrip.flow.worker.env.EnvManager;
import com.hotstrip.flow.worker.env.EnvStrategy;
import com.hotstrip.flow.worker.mapper.NodeMapper;
import com.hotstrip.flow.worker.model.ExecRes;
import com.hotstrip.flow.worker.model.Node;
import com.hotstrip.flow.worker.service.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
public class NodeServiceImpl extends AbstractService<Node, Long, NodeMapper> implements NodeService {

    @Resource
    private EnvManager envManager;

    private static final String[] SKIP_NODE_TYPS = new String[] { "start", "console" };

    @Override
    public ExecRes run(Node node) {
        ExecRes execRes = new ExecRes();
        if (Arrays.asList(SKIP_NODE_TYPS).contains(node.getType())) {
            log.info("skip this node type: {}", node.getType());
            execRes.setCode(0);
            execRes.setOutput("skip this node");
            return execRes;
        }
        try {
            Assert.notNull(node, "node cannot be null");
            Assert.notNull(node.getType(), "node type cannot be null");
            Assert.notNull(node.getNodeData(), "node data cannot be null");

            JSONObject data = node.getNodeData();
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
            log.info("output: {}", output);
            execRes.setCode(0);
            execRes.setOutput(output);
        } catch (Exception e) {
            log.error("execCmd error: {}", e.getMessage(), e);
            execRes.setCode(1);
            execRes.setOutput(e.getMessage());
        }
        return execRes;
    }

    @Override
    public Node save(Node entity) {
        if (entity.getId() == null) {
            entity.setId(IdUtil.getSnowflakeNextId());
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(new Date());
        }
        Assert.isTrue(baseMapper.insert(entity) == 1, Code.SAVE_FAILURE);
        return entity;
    }
}
