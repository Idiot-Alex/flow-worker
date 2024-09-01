package com.hotstrip.flow.worker.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;

import com.github.pagehelper.Page;
import com.github.pagehelper.dialect.AbstractHelperDialect;
import com.github.pagehelper.util.MetaObjectUtil;

public class DuckDBDialect extends AbstractHelperDialect {

  @Override
  public Object processPageParameter(MappedStatement ms, Map<String, Object> paramMap, Page page, BoundSql boundSql,
      CacheKey pageKey) {
    paramMap.put(PAGEPARAMETER_FIRST, page.getStartRow());
    paramMap.put(PAGEPARAMETER_SECOND, page.getPageSize());
    //处理pageKey
    pageKey.update(page.getStartRow());
    pageKey.update(page.getPageSize());
    //处理参数配置
    if (boundSql.getParameterMappings() != null) {
        List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>(boundSql.getParameterMappings());
        if (page.getStartRow() == 0) {
            newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), PAGEPARAMETER_SECOND, int.class).build());
        } else {
            newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), PAGEPARAMETER_FIRST, long.class).build());
            newParameterMappings.add(new ParameterMapping.Builder(ms.getConfiguration(), PAGEPARAMETER_SECOND, int.class).build());
        }
        MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
        metaObject.setValue("parameterMappings", newParameterMappings);
    }
    return paramMap;
  }

  @Override
  public String getPageSql(String sql, Page page, CacheKey pageKey) {
    StringBuilder sqlBuilder = new StringBuilder(sql.length() + 20);
    sqlBuilder.append(sql);
    if (page.getStartRow() == 0) {
        sqlBuilder.append("\n LIMIT ? ");
    } else {
        sqlBuilder.append("\n LIMIT ? OFFSET ? ");
    }
    return sqlBuilder.toString();
  }

}
