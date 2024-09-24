package com.hotstrip.flow.worker.typehandler;

import java.sql.*;

import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import cn.hutool.json.JSONObject;

public class BlobToJsonObjectTypeHandler extends BaseTypeHandler<JSONObject> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType)
          throws SQLException {
    ps.setBytes(i, parameter.toJSONString(0).getBytes());
  }

  @Override
  public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Blob blob = rs.getBlob(columnName);
    return blobToJsonObject(blob);
  }

  @Override
  public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Blob blob = rs.getBlob(columnIndex);
    return blobToJsonObject(blob);
  }

  @Override
  public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Blob blob = cs.getBlob(columnIndex);
    return blobToJsonObject(blob);
  }

  private JSONObject blobToJsonObject(Blob blob) throws SQLException {
    if (blob == null) {
      return null;
    }
    return JSONUtil.parseObj(new String(blob.getBytes(1, (int) blob.length())));
  }

}
