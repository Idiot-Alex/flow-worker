package com.hotstrip.flow.worker.mapper;

import com.hotstrip.flow.worker.model.Node;
import io.mybatis.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;


@Mapper
@Transactional
public interface NodeMapper extends BaseMapper<Node, Long> {

    int updateById(Node entity);

    @Select("select * from node where flow_his_id = #{flowHisId} order by seq_no asc")
    List<Node> findByFlowHisId(@Param("flowHisId") long flowHisId);
}
