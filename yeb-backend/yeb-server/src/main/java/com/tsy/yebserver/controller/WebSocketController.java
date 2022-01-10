package com.tsy.yebserver.controller;

import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.dao.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Steven.T
 * @date 2022/1/10
 */
@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMessage(Authentication authentication, ChatMessage chatMessage){
        final Admin admin = (Admin) authentication.getPrincipal();
        chatMessage.setFrom(admin.getUsername());
        chatMessage.setFromNickName(admin.getName());
        chatMessage.setTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(),"/queue/chat",chatMessage);
    }
}
