package com.hotstrip.flow.worker.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.service.FLowService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FlowController {

    @Resource
    private FLowService flowService;

    @PostMapping("/api/flow/save")
    public ResponseEntity<String> saveFlow(@RequestBody Flow flow) {
        // 参数校验
        if (StrUtil.isBlank(flow.getName())) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Flow name is required");
        }
        if (StrUtil.isBlank(flow.getJsonData())) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Json data is required");
        }

        Flow res = flowService.save(flow);
        log.info("save flow success, flow: {}", JSONUtil.toJsonStr(res));
        return ResponseEntity.ok("Flow saved successfully");
    }
}
