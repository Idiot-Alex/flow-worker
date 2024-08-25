package com.hotstrip.flow.worker.service;

import com.hotstrip.flow.worker.model.Flow;

/**
 * there is a document about mybatis-service:
 * @see https://mapper.mybatis.io/docs/v2.x/5.service.html#_5-3-baseservice
 */
public interface FLowService {

  /**
   * 保存（所有字段）
   *
   * @param entity 实体类
   * @return 返回保存成功后的实体，远程服务调用时，由于序列化和反序列化，入参和返回值不是同一个对象
   */
  Flow save(Flow entity);

  /**
   * 更新（所有字段）
   *
   * @param entity 实体类
   * @return 返回更新成功后的实体，远程服务调用时，由于序列化和反序列化，入参和返回值不是同一个对象
   */
  Flow update(Flow entity);

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
  Flow findById(Long id);

}
