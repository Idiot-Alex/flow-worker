package com.hotstrip.flow.worker.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hotstrip.flow.worker.model.ExecRes;
import com.hotstrip.flow.worker.model.Node;
import com.hotstrip.flow.worker.service.NodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotstrip.flow.worker.mapper.FlowHisMapper;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.model.FlowHis;
import com.hotstrip.flow.worker.service.FlowHisService;
import com.hotstrip.flow.worker.service.FlowService;

import cn.hutool.core.util.IdUtil;
import io.mybatis.common.core.Code;
import io.mybatis.common.util.Assert;
import io.mybatis.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlowHisServiceImpl extends AbstractService<FlowHis, Long, FlowHisMapper> implements FlowHisService {

  @Resource
  private FlowService flowService;
  @Resource
  private FlowHisMapper flowHisMapper;
  @Resource
  private NodeService nodeService;

  public Page<FlowHis> findByPage(int page, int size, FlowHis info) {
    PageHelper.startPage(page, size);
    List<FlowHis> list = flowHisMapper.list(info);
    return (Page<FlowHis>) list;
  }

  @Override
  public FlowHis updateById(FlowHis entity) {
    if (entity.getUpdatedAt() == null) {
      entity.setUpdatedAt(new Date());
    }
    Assert.isTrue(baseMapper.updateById(entity) == 1, Code.UPDATE_FAILURE);
    return entity;
  }

  @Override
  public FlowHis save(FlowHis entity) {
    if (entity.getId() == null) {
      entity.setId(IdUtil.getSnowflakeNextId());
    }
    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(new Date());
    }
    Assert.isTrue(baseMapper.insert(entity) == 1, Code.SAVE_FAILURE);
    return entity;
  }

  @Override
  @Transactional
  public FlowHis initFlowHis(Flow flow) {
    Assert.notNull(flow, "flow is null");
    // save flow his
    int maxSeqNo = flowHisMapper.findMaxSeqNoByFlowId(flow.getId());
    FlowHis flowHis = new FlowHis();
    flowHis.setFlowId(flow.getId());
    flowHis.setSeqNo(maxSeqNo + 1);
    flowHis.setJsonData(flow.getJsonData());
    FlowHis flowHisSaved = this.save(flowHis);

    // load params
    JSONObject jsonData = flow.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    JSONObject sort = jsonData.getJSONObject("sort");
    List<Long> order = sort.getBeanList("order", Long.class);

    // save node
    int orderNum = 0;
    for (Long nodeId : order) {
      Node node = nodes.stream().filter(n -> nodeId.equals(n.getId())).findFirst().get();
      log.info("node: {}", JSONUtil.toJsonStr(node));
      node.setFlowHisId(flowHisSaved.getId());
      node.setSeqNo(orderNum);

      // init node data
      JSONObject nodeData = node.getData();
      nodeData.remove("execRes");
      nodeData.set("isRunning", false);
      nodeData.set("isFinished", false);
      nodeData.set("hasError", false);
      nodeData.set("isSkipped", false);
      nodeService.save(node);
      log.info("node: {}", JSONUtil.toJsonStr(node));
      orderNum++;
    }

    return flowHisSaved;
  }

  @Override
  public void run(long flowHisId) {
    resetExecStatus(flowHisId);

    List<Node> nodeList = nodeService.findByFlowHisId(flowHisId);
    log.info("nodeList: {}", JSONUtil.toJsonStr(nodeList));
    nodeList.forEach(node -> {
      // start run node
      startExecNode(flowHisId, node.getId());
      Node executedNode = nodeService.run(node);
      // sync exec node status
      syncExecNodeStatus(flowHisId, executedNode);
    });

    // run end
    finishExecStatus(flowHisId);
  }

  private void finishExecStatus(long flowHisId) {
    FlowHis flowHis = findById(flowHisId);
    flowHis.setEndAt(new Date());
    flowHisMapper.updateById(flowHis);
  }

  private void syncExecNodeStatus(long flowHisId, Node executedNode) {
    FlowHis flowHis = findById(flowHisId);
    JSONObject jsonData = flowHis.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);

    nodes.forEach(node -> {
      if (node.getId() == executedNode.getId()) {
//        node.setData(executedNode.getData());
//        node = executedNode;
        BeanUtil.copyProperties(executedNode, node);
      }
    });

    jsonData.set("nodes", nodes);
    flowHis.setJsonData(jsonData);
    flowHisMapper.updateById(flowHis);
  }

  private void startExecNode(Long flowHisId, Long nodeId) {
    FlowHis flowHis = findById(flowHisId);
    JSONObject jsonData = flowHis.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);

    nodes.forEach(node -> {
      if (node.getId() == nodeId) {
        JSONObject nodeData = node.getData();
        nodeData.set("isRunning", true);
        nodeData.set("isFinished", false);
        nodeData.set("hasError", false);
        nodeData.set("isSkipped", false);
      }
    });

    jsonData.set("nodes", nodes);
    flowHis.setJsonData(jsonData);
    flowHisMapper.updateById(flowHis);
  }

  private void resetExecStatus(long flowHisId) {
    FlowHis flowHis = findById(flowHisId);
    JSONObject jsonData = flowHis.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);

    // reset exec status
    nodes.forEach(node -> {
      JSONObject nodeData = node.getData();
      nodeData.remove("execRes");
      nodeData.set("isRunning", false);
      nodeData.set("isFinished", false);
      nodeData.set("hasError", false);
      nodeData.set("isSkipped", false);
    });

    jsonData.set("nodes", nodes);
    flowHis.setJsonData(jsonData);
    flowHis.setStartAt(new Date());
    flowHis.setEndAt(null);

    flowHisMapper.updateById(flowHis);
  }

  private List<Node> getNodeListById(long id) {
    FlowHis flowHis = this.findById(id);
    JSONObject jsonData = flowHis.getJsonData();
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    return nodes;
  }

}
