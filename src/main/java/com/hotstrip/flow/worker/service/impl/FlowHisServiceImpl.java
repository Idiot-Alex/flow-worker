package com.hotstrip.flow.worker.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
    // save flow his
    int maxSeqNo = flowHisMapper.findMaxSeqNoByFlowId(flow.getId());
    FlowHis flowHis = new FlowHis();
    flowHis.setFlowId(flow.getId());
    flowHis.setSeqNo(maxSeqNo + 1);
    flowHis.setJsonData(flow.getJsonData());
    FlowHis flowHisSaved = this.save(flowHis);

    // load params
    JSONObject jsonData = JSONUtil.parseObj(flow.getJsonData());
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    JSONObject sort = jsonData.getJSONObject("sort");
    List<String> order = sort.getBeanList("order", String.class);

    // save node
    int orderNum = 0;
    for (String nodeId : order) {
      Node node = nodes.stream().filter(n -> nodeId.equals(n.getId())).findFirst().get();
      node.setFlowHisId(flowHisSaved.getId());
      node.setOrder(orderNum);
      nodeService.save(node);
      orderNum++;
    }

    return flowHisSaved;
  }

  @Override
  public void run(long id) {
    resetExecStatus(id);

    FlowHis flowHis = findById(id);
    JSONObject jsonData = JSONUtil.parseObj(flowHis.getJsonData());
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    JSONObject sort = jsonData.getJSONObject("sort");
    List<String> order = sort.getBeanList("order", String.class);
    JSONObject predecessors = sort.getJSONObject("predecessors");

    // foreach order node
    order.forEach(o -> {
      // check node's predecessors FIXME maybe need refactor
//      List<String> preOrderList = predecessors.getBeanList(o, String.class);
//      preOrderList.forEach(pre -> {
//        Node node = nodes.stream().filter(n -> o.equals(n.getId())).findFirst().get();
//        ExecRes execRes = nodeService.run(node);
//        node.getData().set("execRes", execRes);
//      });

      // exec order node
      Node node = nodes.stream().filter(n -> o.equals(n.getId())).findFirst().get();
      ExecRes execRes = nodeService.run(node);
      node.getNodeData().set("execRes", execRes);
    });

    log.info("executed nodes: {}", JSONUtil.toJsonStr(nodes));
  }

  private void resetExecStatus(long id) {
    FlowHis flowHis = findById(id);
    JSONObject jsonData = JSONUtil.parseObj(flowHis.getJsonData());
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);

    // reset exec status
    nodes.forEach(node -> {
      JSONObject nodeData = node.getNodeData();
      nodeData.remove("execRes");
      nodeData.set("isRunning", false);
      nodeData.set("isFinished", false);
      nodeData.set("hasError", false);
      nodeData.set("isSkipped", false);
    });

    jsonData.set("nodes", nodes);
    flowHis.setJsonData(jsonData.toJSONString(2));
    flowHis.setStartAt(new Date());
    flowHis.setEndAt(null);

    flowHisMapper.updateById(flowHis);
  }

  private List<Node> getNodeListById(long id) {
    FlowHis flowHis = this.findById(id);
    JSONObject jsonData = JSONUtil.parseObj(flowHis.getJsonData());
    List<Node> nodes = jsonData.getBeanList("nodes", Node.class);
    return nodes;
  }

}
