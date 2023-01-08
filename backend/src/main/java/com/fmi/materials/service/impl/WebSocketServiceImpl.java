package com.fmi.materials.service.impl;

import com.fmi.materials.service.WebSocketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private Logger logger = LoggerFactory.getLogger(WebSocketServiceImpl.class);

    private void sendMessage(String topic, String prefix) {
        String formattedTopic = this.sanitize(topic);
        String formattedPrefix = this.sanitize(prefix);
        String fullPath = "/" + formattedPrefix + "/" + formattedTopic;

        this.logger.info("sending to " + fullPath);
        this.messagingTemplate.convertAndSend(fullPath, "");
    }

    private void sendMessageUser(Long userId, String topic, @Payload String message) {
        this.logger.info("sending to user with id " + userId + " on topic " + topic + " message: " + message);
        this.messagingTemplate.convertAndSendToUser(userId.toString(), topic, message);
    }

    private String sanitize(String path) {
        return path.trim().replaceAll("/+", "/").replaceFirst("/", "").replaceAll("/+$", "");
    }

    @Override
    public void notifyFrontend(String topic, String prefix) {
        this.sendMessage(topic, prefix);
    }

    @Override
    public void notifyFrontendUser(Long userId, String topic) {
        this.sendMessageUser(userId, topic, "");
    }
}
