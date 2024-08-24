package com.hostrip.flow.worker;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hotstrip.flow.worker.mapper.FlowMapper;
import com.hotstrip.flow.worker.model.Flow;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WorkerAppTest {
    @Resource
    private FlowMapper flowMapper;

    @Test
    public void test() {
        log.info("test");
        Flow flow = flowMapper.findById(1L);
        log.info("flow: {}", flow);
    }
}
