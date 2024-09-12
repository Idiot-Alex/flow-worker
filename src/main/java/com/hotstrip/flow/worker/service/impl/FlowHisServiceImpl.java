package com.hotstrip.flow.worker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotstrip.flow.worker.mapper.FlowHisMapper;
import com.hotstrip.flow.worker.model.FlowHis;
import com.hotstrip.flow.worker.service.FLowHisService;
import io.mybatis.common.core.Code;
import io.mybatis.common.util.Assert;
import io.mybatis.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlowHisServiceImpl extends AbstractService<FlowHis, Long, FlowHisMapper> implements FLowHisService {

  @Resource
  private FlowHisMapper flowHisMapper;

  public Page<FlowHis> findByPage(int page, int size, FlowHis info) {
    PageHelper.startPage(page, size);
    List<FlowHis> list = flowHisMapper.list(info);
    return (Page<FlowHis>) list;
  }

  @Override
  public FlowHis updateById(FlowHis entity) {
    Assert.isTrue(baseMapper.updateById(entity) == 1, Code.UPDATE_FAILURE);
    return entity;
  }

}
