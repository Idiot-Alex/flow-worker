package com.hotstrip.flow.worker.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FlowController {

    @GetMapping("/api/flow")
    public String flow() {
        log.info("flow");
        return "flow";
    }
}
