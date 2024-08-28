package com.hotstrip.flow.worker.env;

public interface EnvStrategy {

  String name();

  String path();

  String version();

  Env info();

}
