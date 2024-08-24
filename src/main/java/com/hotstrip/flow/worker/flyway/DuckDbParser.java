package com.hotstrip.flow.worker.flyway;

import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.internal.parser.Parser;
import org.flywaydb.core.internal.parser.ParsingContext;

public class DuckDbParser extends Parser {

  protected DuckDbParser(Configuration configuration, ParsingContext parsingContext) {
    super(configuration, parsingContext, 3);
  }

}
