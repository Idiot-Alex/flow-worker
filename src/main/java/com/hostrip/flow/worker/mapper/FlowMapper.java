package com.hostrip.flow.worker.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hostrip.flow.worker.model.Flow;

@Mapper
public interface FlowMapper {

    @Select("SELECT * FROM flow WHERE id = #{id}")
    Flow findById(Long id);

}
