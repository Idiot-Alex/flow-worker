package com.hotstrip.flow.worker.model;

import java.util.Date;

import lombok.Data;

@Data
public class Flow {
  private Long id;
  private String name;
  private String jsonData;
  private Date createdAt;
  private Date updatedAt;
}
