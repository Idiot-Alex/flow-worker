package com.hotstrip.flow.worker.flyway;

import java.sql.Connection;

import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.internal.database.base.Database;
import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcConnectionFactory;
import org.flywaydb.core.internal.jdbc.StatementInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * DuckDB database type
 */
@Slf4j
public class DuckDBDatabase extends Database<DuckDBConnection> {

  public DuckDBDatabase(Configuration configuration, JdbcConnectionFactory jdbcConnectionFactory,
      StatementInterceptor statementInterceptor) {
    super(configuration, jdbcConnectionFactory, statementInterceptor);
  }

  @Override
  protected DuckDBConnection doGetConnection(Connection connection) {
    return new DuckDBConnection(this, connection);
  }

  @Override
  public void ensureSupported() {
    log.info("DuckDBDatabase is supported");
  }

  @Override
  public boolean supportsDdlTransactions() {
    return true;
  }

  @Override
  public boolean supportsChangingCurrentSchema() {
    return true;
  }

  @Override
  public String getBooleanTrue() {
    return "1";
  }

  @Override
  public String getBooleanFalse() {
    return "0";
  }

  @Override
  public boolean catalogIsSchema() {
    return false;
  }

  @Override
  public String getRawCreateScript(Table table, boolean baseline) {
    return "CREATE TABLE " + table + " (\n" +
    "    \"installed_rank\" INTEGER NOT NULL PRIMARY KEY,\n" +
    "    \"version\" VARCHAR(50),\n" +
    "    \"description\" VARCHAR(200) NOT NULL,\n" +
    "    \"type\" VARCHAR(20) NOT NULL,\n" +
    "    \"script\" VARCHAR(1000) NOT NULL,\n" +
    "    \"checksum\" INTEGER,\n" +
    "    \"installed_by\" VARCHAR(100) NOT NULL,\n" +
    "    \"installed_on\" TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),\n" +
    "    \"execution_time\" INTEGER NOT NULL,\n" +
    "    \"success\" BOOLEAN NOT NULL\n" +
    ");\n" +
    (baseline ? getBaselineStatement(table) + ";\n" : "") +
    "CREATE INDEX \"" + table.getName() + "_s_idx\" ON \"" + table.getName() + "\" (\"success\");";
  }

}
