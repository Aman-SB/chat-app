package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Enumerator.ChatEvent;
import com.aman.chat_application.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // Handles messages sent to /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDTO sendMessage(@Payload MessageCreateDTO messageCreateDTO) {
        return chatService.saveMessage(messageCreateDTO);
    }

    // Handles messages sent to /app/chat.addUser
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MessageDTO addUser(@Payload MessageCreateDTO messageCreateDTO) {
        return chatService.addUserToChat(messageCreateDTO);
    }

}
