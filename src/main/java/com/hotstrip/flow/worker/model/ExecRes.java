package com.hotstrip.flow.worker.model;

import lombok.Data;

@Data
public class ExecRes {
    // {0: success, 1: error, 2: skipped}
    private int code;
    private String output;
}
