package com.hotstrip.flow.worker.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotstrip.flow.worker.service.FLowService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FlowController {

    @Resource
    private FLowService flowService;

    @GetMapping("/api/flow")
    public String flow() {
        log.info("flow");
        return "flow";
    }
}
