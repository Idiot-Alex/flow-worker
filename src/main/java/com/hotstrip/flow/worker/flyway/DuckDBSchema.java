package com.hotstrip.flow.worker.flyway;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.internal.database.base.Schema;
import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

public class DuckDBSchema extends Schema<DuckDBDatabase, DuckDBTable> {

  public DuckDBSchema(JdbcTemplate jdbcTemplate, DuckDBDatabase database, String name) {
    super(jdbcTemplate, database, name);
  }

  @Override
  protected boolean doExists() throws SQLException {
  return jdbcTemplate.queryForInt("SELECT count(*) FROM information_schema.schemata WHERE schema_name = '" + name + "'") > 0;
  }

  @Override
  protected boolean doEmpty() throws SQLException {
    Table[] tables = allTables();
    List<String> tableNames = new ArrayList<>();
    for (Table table : tables) {
      String tableName = table.getName();
      tableNames.add(tableName);
    }
    return tableNames.isEmpty();
  }

  @Override
  protected void doCreate() throws SQLException {
    jdbcTemplate.execute("CREATE SCHEMA " + database.quote(name));
  }

  @Override
  protected void doDrop() throws SQLException {
    jdbcTemplate.execute("DROP SCHEMA " + database.quote(name) + " CASCADE");
  }

  @Override
  protected void doClean() throws SQLException {
    List<String> viewNames = jdbcTemplate.queryForStringList("SELECT view_name FROM information_schema.views WHERE table_schema = '" + name + "'");

    for (String viewName : viewNames) {
        jdbcTemplate.execute("DROP VIEW " + database.quote(name, viewName));
    }

    for (Table table : allTables()) {
        table.drop();
    }
  }

  @Override
  protected DuckDBTable[] doAllTables() throws SQLException {
    List<String> tableNames = jdbcTemplate.queryForStringList("SELECT table_name FROM information_schema.tables WHERE table_schema = '" + name + "'");

    DuckDBTable[] tables = new DuckDBTable[tableNames.size()];
    for (int i = 0; i < tableNames.size(); i++) {
      tables[i] = new DuckDBTable(jdbcTemplate, database, this, tableNames.get(i));
    }
    return tables;
  }

  @Override
  public Table getTable(String tableName) {
    return new DuckDBTable(jdbcTemplate, database, this, tableName);
  }

}
