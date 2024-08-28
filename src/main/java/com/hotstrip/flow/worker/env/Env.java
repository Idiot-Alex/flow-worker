package com.hotstrip.flow.worker.env;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Env {

  private String name;
  private String path;
  private String version;

}
