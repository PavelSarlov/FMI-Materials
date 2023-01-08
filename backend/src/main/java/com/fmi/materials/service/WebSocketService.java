package com.fmi.materials.service;

public interface WebSocketService {
    void notifyFrontend(String topic, String prefix);

    void notifyFrontendUser(Long userId, String topic);
}
