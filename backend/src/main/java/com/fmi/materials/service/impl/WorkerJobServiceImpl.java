package com.fmi.materials.service.impl;

import javax.transaction.Transactional;

import com.fmi.materials.model.WorkerJob;
import com.fmi.materials.repository.WorkerJobRepository;
import com.fmi.materials.service.WorkerJobService;
import com.fmi.materials.vo.WorkerJobStatus;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerJobServiceImpl implements WorkerJobService {
    private final WorkerJobRepository workerJobRepository;

    @Override
    @Transactional
    public void createEmailJob(String to, String subject, String type, JSONObject data) {
        data.put("to", to);
        data.put("subject", subject);
        data.put("type", type);

        this.workerJobRepository.save(new WorkerJob("email", data, WorkerJobStatus.PENDING));
    }
}
