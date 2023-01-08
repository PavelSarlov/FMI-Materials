package com.fmi.materials.service;

import org.json.JSONObject;

public interface WorkerJobService {
    public void createEmailJob(String to, String subject, String type, JSONObject data);
}
