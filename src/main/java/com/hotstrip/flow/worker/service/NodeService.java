package com.hotstrip.flow.worker.service;

import java.util.List;

import com.hotstrip.flow.worker.model.ExecRes;
import com.hotstrip.flow.worker.model.Node;

public interface NodeService {

    ExecRes run(Node node);

    Node save(Node node);

    Node findById(Long id);

    Node updateById(Node entity);

    int deleteById(Long id);

    List<Node> findByFlowHisId(long flowHisId);
}
