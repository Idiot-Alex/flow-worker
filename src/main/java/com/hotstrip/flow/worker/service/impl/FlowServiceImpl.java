package com.hotstrip.flow.worker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotstrip.flow.worker.mapper.FlowMapper;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.service.FLowService;

import io.mybatis.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlowServiceImpl extends AbstractService<Flow, Long, FlowMapper> implements FLowService {

  @Resource
  private FlowMapper flowMapper;

  public Page<Flow> findByPage(int page, int size, Flow info) {
    PageHelper.startPage(page, size);
    List<Flow> list = flowMapper.selectList(info);
    return (Page<Flow>) list;
  }

}
