package com.hotstrip.flow.worker.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
  public FlowHis initFlowHis(Flow flow) {
    int maxSeqNo = flowHisMapper.findMaxSeqNoByFlowId(flow.getId());
    FlowHis flowHis = new FlowHis();
    flowHis.setFlowId(flow.getId());
    flowHis.setSeqNo(maxSeqNo + 1);
    flowHis.setJsonData(flow.getJsonData());
    return this.save(flowHis);
  }

}
