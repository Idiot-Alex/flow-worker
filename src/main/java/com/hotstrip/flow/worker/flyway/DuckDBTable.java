package com.hotstrip.flow.worker.flyway;

import java.sql.SQLException;

import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * DuckDB 表
 */
@Slf4j
public class DuckDBTable extends Table<DuckDBDatabase, DuckDBSchema> {

  public DuckDBTable(JdbcTemplate jdbcTemplate, DuckDBDatabase database, DuckDBSchema schema, String name) {
    super(jdbcTemplate, database, schema, name);
  }

  @Override
  protected boolean doExists() throws SQLException {
    return jdbcTemplate.queryForInt("SELECT count(*) FROM information_schema.tables WHERE table_schema = '" + schema.getName() + "' AND table_name = '" + name + "'") > 0;
  }

  @Override
  protected void doLock() throws SQLException {
    // DuckDB 不支持显式的锁机制，因此这里不需要实现 doLock 方法。
    log.debug("Unable to lock " + this + " as DuckDB does not support locking. No concurrent migration supported.");
  }

  @Override
  protected void doDrop() throws SQLException {
    String dropSql = "DROP TABLE " + database.quote(schema.getName(), name);
    // DuckDB does not support disabling foreign keys like SQLite, so the PRAGMA statement is not necessary.
    jdbcTemplate.execute(dropSql);
  }

}
