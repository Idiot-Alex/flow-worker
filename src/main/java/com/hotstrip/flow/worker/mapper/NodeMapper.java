package com.hotstrip.flow.worker.mapper;

import io.mybatis.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import com.hotstrip.flow.worker.model.Node;

import org.springframework.transaction.annotation.Transactional;


@Mapper
@Transactional
public interface NodeMapper extends BaseMapper<Node, Long> {

}
