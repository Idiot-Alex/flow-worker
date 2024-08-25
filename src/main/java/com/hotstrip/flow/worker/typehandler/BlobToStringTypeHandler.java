package com.hotstrip.flow.worker.typehandler;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Blob 转 String 类型处理器
 */
public class BlobToStringTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBytes(i, parameter.getBytes());
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Blob blob = rs.getBlob(columnName);
    return blobToString(blob);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Blob blob = rs.getBlob(columnIndex);
    return blobToString(blob);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Blob blob = cs.getBlob(columnIndex);
    return blobToString(blob);
  }

  private String blobToString(Blob blob) throws SQLException {
    if (blob == null) {
      return null;
    }
    return new String(blob.getBytes(1, (int) blob.length()));
  }

}
