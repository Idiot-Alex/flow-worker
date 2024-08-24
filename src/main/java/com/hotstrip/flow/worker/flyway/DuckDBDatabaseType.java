package com.hotstrip.flow.worker.flyway;

import java.sql.Connection;
import java.sql.Types;

import org.flywaydb.core.api.ResourceProvider;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.internal.database.base.BaseDatabaseType;
import org.flywaydb.core.internal.database.base.Database;
import org.flywaydb.core.internal.jdbc.JdbcConnectionFactory;
import org.flywaydb.core.internal.jdbc.StatementInterceptor;
import org.flywaydb.core.internal.parser.Parser;
import org.flywaydb.core.internal.parser.ParsingContext;

import lombok.extern.slf4j.Slf4j;

/**
 * support duckdb with flyway
 */
@Slf4j
public class DuckDBDatabaseType extends BaseDatabaseType {

  @Override
  public String getName() {
    return "DuckDB";
  }

  @Override
  public int getNullType() {
    return Types.VARCHAR;
  }

  @Override
  public boolean handlesJDBCUrl(String url) {
    return url.startsWith("jdbc:duckdb:") || url.startsWith("jdbc:p6spy:duckdb:");
  }

  @Override
  public String getDriverClass(String url, ClassLoader classLoader) {
    if (url.startsWith("jdbc:p6spy:duckdb:")) {
      return "com.p6spy.engine.spy.P6SpyDriver";
    }
    return "org.duckdb.DuckDBDriver";
  }

  @Override
  public boolean handlesDatabaseProductNameAndVersion(String databaseProductName, String databaseProductVersion, Connection connection) {
    log.info("Handling database product name: {}, version: {}", databaseProductName, databaseProductVersion);
    return databaseProductName.startsWith("DuckDB");
  }

  @Override
  public Database createDatabase(Configuration configuration, JdbcConnectionFactory jdbcConnectionFactory, StatementInterceptor statementInterceptor) {
    return new DuckDBDatabase(configuration, jdbcConnectionFactory, statementInterceptor);
  }

  @Override
  public Parser createParser(Configuration configuration, ResourceProvider resourceProvider, ParsingContext parsingContext) {
    return new DuckDbParser(configuration, parsingContext);
  }

}
