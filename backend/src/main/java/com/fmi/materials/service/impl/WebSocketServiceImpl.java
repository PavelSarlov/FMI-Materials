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

    private void sendMessage(String topicSuffix) {
        this.logger.info("sending to topic " + topicSuffix);
        this.messagingTemplate.convertAndSend("/topic/" + topicSuffix, "");
    }

    private void sendMessageUser(String username, String topicSuffix, @Payload String message) {
        this.logger.info("sending to user " + username + " on topic " + topicSuffix + " message: " + message);
        this.messagingTemplate.convertAndSendToUser(username, "/queue/" + topicSuffix, message);
    }

    public void notifyFronted(String topicSuffix) {
        this.sendMessage(topicSuffix);
    }

    @Override
    public void notifyFrontedUser(String username, String topicSuffix) {
        this.sendMessageUser(username, topicSuffix, "");
    }
}
