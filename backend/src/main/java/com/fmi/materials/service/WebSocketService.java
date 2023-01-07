package com.fmi.materials.service;

public interface WebSocketService {
    void notifyFronted(String topicSuffix);

    void notifyFrontedUser(String username, String topicSuffix);
}
