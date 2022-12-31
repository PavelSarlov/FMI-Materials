package com.fmi.materials.service.impl;

import com.fmi.materials.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    private void sendMessage(String topicSuffix) {
        messagingTemplate.convertAndSend("/topic/" + topicSuffix);
    }

    private void sendMessageUser(String username, String topicSuffix, @Payload String message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/" + topicSuffix, message);
    }

    @Override
    public void notifyFronted(String topicSuffix) {
        this.sendMessage(topicSuffix);
    }

    @Override
    public void notifyFrontedUser(String username, String topicSuffix) {
        this.sendMessageUser(username, topicSuffix, "");
    }
}
