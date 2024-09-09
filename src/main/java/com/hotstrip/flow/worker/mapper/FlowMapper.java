package com.hotstrip.flow.worker.mapper;

import io.mybatis.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.hotstrip.flow.worker.model.Flow;
import org.springframework.transaction.annotation.Transactional;


@Mapper
@Transactional
public interface FlowMapper extends BaseMapper<Flow, Long> {

    @Deprecated
    @Insert("INSERT INTO flow (id, name, json_data) VALUES (#{id}, #{name}, #{jsonData})")
    int insertFlow(Flow flow);

    int updateById(Flow entity);

    List<Flow> list(Flow entity);

}
