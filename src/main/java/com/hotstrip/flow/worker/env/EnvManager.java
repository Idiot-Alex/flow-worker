package com.hotstrip.flow.worker.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class EnvManager {
    private final Map<String, EnvStrategy> envMap;

    public EnvManager(Map<String, EnvStrategy> envMap) {
        this.envMap = envMap;
    }

    public EnvStrategy getEnv(String envName) {
        if (!envName.endsWith("Env")) {
            envName += "Env";
        }
        return envMap.get(envName);
    }
}
