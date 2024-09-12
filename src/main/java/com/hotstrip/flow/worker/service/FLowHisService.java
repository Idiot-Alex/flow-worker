package com.hotstrip.flow.worker.service;

import com.github.pagehelper.Page;
import com.hotstrip.flow.worker.model.FlowHis;

/**
 * there is a document about mybatis-service:
 * @see https://mapper.mybatis.io/docs/v2.x/5.service.html#_5-3-baseservice
 */
public interface FLowHisService {

  /**
   * 保存（所有字段）
   *
   * @param entity 实体类
   * @return 返回保存成功后的实体，远程服务调用时，由于序列化和反序列化，入参和返回值不是同一个对象
   */
  FlowHis save(FlowHis entity);

  /**
   * 更新（所有字段）
   * DuckDB 不支持 update table set id = 1 where id = 1 
   *
   * @param entity 实体类
   * @return 返回更新成功后的实体，远程服务调用时，由于序列化和反序列化，入参和返回值不是同一个对象
   */
  FlowHis updateById(FlowHis entity);

  /**
   * 根据主键进行删除
   *
   * @param id 指定的主键
   * @return 返回 1成功，0失败抛出异常
   */
  int deleteById(Long id);

   /**
   * 根据指定的主键查询
   *
   * @param id 主键
   * @return 实体
   */
  FlowHis findById(Long id);

  /**
   * 分页查询
   *
   * @param page 页码
   * @param size 每页大小
   * @param info 查询条件
   * @return 分页结果
   */
  Page<FlowHis> findByPage(int page, int size, FlowHis info);

}
