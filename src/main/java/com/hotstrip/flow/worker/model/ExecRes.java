package com.hotstrip.flow.worker.model;

import lombok.Data;

@Data
public class ExecRes {
    // 0 is success exit
    private int exitCode;
    private String output;
}
