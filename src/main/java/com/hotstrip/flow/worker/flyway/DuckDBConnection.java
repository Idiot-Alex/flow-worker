package com.hotstrip.flow.worker.flyway;

import java.sql.SQLException;

import org.flywaydb.core.internal.database.base.Connection;
import org.flywaydb.core.internal.database.base.Schema;

public class DuckDBConnection extends Connection<DuckDBDatabase> {

  protected DuckDBConnection(DuckDBDatabase database, java.sql.Connection connection) {
    super(database, connection);
  }

  @Override
  protected String getCurrentSchemaNameOrSearchPath() throws SQLException {
    return "main";
  }

  @Override
  public Schema getSchema(String name) {
    return new DuckDBSchema(jdbcTemplate, database, name);
  }

}
