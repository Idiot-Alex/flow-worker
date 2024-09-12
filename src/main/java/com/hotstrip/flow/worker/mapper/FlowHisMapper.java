package com.hotstrip.flow.worker.mapper;

import io.mybatis.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.hotstrip.flow.worker.model.FlowHis;

import org.springframework.transaction.annotation.Transactional;


@Mapper
@Transactional
public interface FlowHisMapper extends BaseMapper<FlowHis, Long> {

    @Deprecated
    @Insert("INSERT INTO flow_his (id, flow_id, json_data) VALUES (#{id}, #{flowId}, #{jsonData})")
    int insertFlowHis(FlowHis entity);

    int updateById(FlowHis entity);

    List<FlowHis> list(FlowHis entity);

}
