package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.Chat.ChatCreateDTO;
import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Chat.ChatUpdateDTO;
import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Dto.UserDto.UserProfileDto;
import com.aman.chat_application.Service.ChatService;
import com.aman.chat_application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService,UserService userService) {
        this.chatService = chatService;
        this.userService=userService;
    }

    // Handles messages sent to /app/chat.sendMessage
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public MessageDTO sendMessage(@Payload MessageCreateDTO messageCreateDTO) {
        return chatService.saveMessage(messageCreateDTO);
    }

    // Handles messages sent to /app/chat.addUser
    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public MessageDTO addUser(@Payload MessageCreateDTO messageCreateDTO, SimpMessageHeaderAccessor headerAccessor) {
        return chatService.addUserToChat(messageCreateDTO,headerAccessor);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> getChatDetails(@PathVariable Integer chatId) {
        ChatDTO chatDTO = chatService.getChatDetails(chatId);
        return ResponseEntity.ok(chatDTO);
    }

    @PostMapping("/{chatName}")
    public ResponseEntity<ChatDTO> createChat(@PathVariable String chatName) {
        ChatDTO chatDTO = chatService.createChat(chatName);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatDTO);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Integer chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }

    @PutMapping("/update/{chatId}")
    public ResponseEntity<ChatDTO> updateChat(@PathVariable Integer chatId, @RequestParam String chatName) {
        ChatDTO chatDTO = chatService.updateChat(chatId, chatName);
        return ResponseEntity.ok(chatDTO);
    }

    @GetMapping("/user/{userId}/chats")
    public ResponseEntity<List<ChatDTO>> getUserChats(@PathVariable Integer userId) {
        List<ChatDTO> chats = chatService.getUserChats(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<MessageDTO>> getMessagesInChat(@PathVariable Integer chatId) {
        List<MessageDTO> messages = chatService.getMessagesInChat(chatId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Integer userId) {
        UserProfileDto userProfileDto = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfileDto);
    }

    //unsafe api
    @GetMapping("/search")
    public ResponseEntity<List<ChatDTO>> searchChats(@RequestParam String name) {
        List<ChatDTO> chats = chatService.searchChats(name);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/user/{userId}/active-chats")
    public ResponseEntity<List<ChatDTO>> getUserActiveChats(@PathVariable Integer userId) {
        List<ChatDTO> activeChats = chatService.getUserActiveChats(userId);
        return ResponseEntity.ok(activeChats);
    }

    @DeleteMapping("/{chatId}/leave")
    public ResponseEntity<String> leaveChat(@PathVariable Integer chatId, @RequestParam Integer userId) {
        chatService.leaveChat(chatId, userId);
        return ResponseEntity.ok("Successfully left the chat");
    }

}
