package com.hotstrip.flow.worker.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.hotstrip.flow.worker.model.Flow;


@Mapper
public interface FlowMapper extends io.mybatis.mapper.Mapper<Flow, Long> {

}
