package com.hotstrip.flow.worker.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.hotstrip.flow.worker.model.Flow;
import com.hotstrip.flow.worker.model.R;
import com.hotstrip.flow.worker.model.RMap;
import com.hotstrip.flow.worker.service.FLowService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FlowController {

    @Resource
    private FLowService flowService;

    // save flow
    @PostMapping("/api/flow/save")
    public ResponseEntity<R> saveFlow(@RequestBody Flow flow) {
        // 参数校验
        if (StrUtil.isBlank(flow.getName())) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(R.error("Flow name is required"));
        }
        if (StrUtil.isBlank(flow.getJsonData())) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(R.error("Json data is required"));
        }

        if (flow.getId() != null) {
            // query flow by id
            Flow res = flowService.findById(flow.getId());
            if (res == null) {
                // return error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error("flow not found"));
            }
            // update flow
            res = flowService.updateById(flow);
            log.info("update flow success, flow: {}", JSONUtil.toJsonStr(res));
        } else {
            // insert flow
            Flow res = flowService.save(flow);
            log.info("save flow success, flow: {}", JSONUtil.toJsonStr(res));
        }
        return ResponseEntity.ok(R.ok("Flow saved successfully"));
    }

    // delete flow by id
    @PostMapping("/api/flow/delete/{id}")
    public ResponseEntity<R> deleteFlow(@PathVariable("id") Long id) {
        int rows = flowService.deleteById(id);
        if (rows > 0) {
            return ResponseEntity.ok(R.ok("Flow deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error("Flow deleted failed"));
        }
    }

    // query flow with page
    @PostMapping("/api/flow/list")
    public ResponseEntity<RMap> listFlow(@RequestParam int page, @RequestParam int size, @RequestBody Flow info) {
        Page<Flow> list = flowService.findByPage(page, size, info);
        RMap rMap = RMap.ok("Flow query success", list);
        rMap.initPage(list);
        return ResponseEntity.ok(rMap);
    }

    // query flow by id
    @GetMapping("/api/flow/{id}")
    public ResponseEntity<R> getFlowById(@PathVariable("id") Long id) {
        // id cannot null
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.error("Flow ID is required"));
        }
        Flow flow = flowService.findById(id);
        if (flow == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(R.error("Flow not found"));
        }
        return ResponseEntity.ok(R.ok("succes", flow));
    }
    
}
