package com.hotstrip.flow.worker.service.impl;

import org.springframework.stereotype.Service;

import com.hotstrip.flow.worker.mapper.FlowMapper;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.service.FLowService;

import io.mybatis.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlowServiceImpl extends AbstractService<Flow, Long, FlowMapper> implements FLowService {

}
