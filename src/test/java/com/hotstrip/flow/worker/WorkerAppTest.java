package com.hotstrip.flow.worker;

import com.hotstrip.flow.worker.WorkerApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = WorkerApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkerAppTest {

    @Test
    public void test() {
        log.info("test");
    }
}
