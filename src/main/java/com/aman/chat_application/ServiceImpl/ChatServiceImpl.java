package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Enumerator.ChatEvent;
import com.aman.chat_application.Exception.ChatNotFoundException;
import com.aman.chat_application.Exception.UserNotFoundException;
import com.aman.chat_application.Mapper.MessageMapper;
import com.aman.chat_application.Model.Chat;
import com.aman.chat_application.Model.Message;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.ChatRepository;
import com.aman.chat_application.Repository.MessageRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.ChatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, MessageRepository messageRepository,UserRepository userRepository,SimpMessagingTemplate messagingTemplate) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public MessageDTO saveMessage(MessageCreateDTO messageCreateDTO) {
        // Convert DTO to Message entity and save it
        Message message = MessageMapper.INSTANCE.mapperMessageFromCreate(messageCreateDTO);

        // Fetch the chat and set it to the message (simplified for brevity)
        Chat chat = chatRepository.findById(messageCreateDTO.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat not found "));

        message.setChat(chat);
        messageRepository.save(message);

        return MessageMapper.INSTANCE.mapperMessageToDto(message); // Or map the saved entity back to DTO
    }

    @Override
    public MessageDTO addUserToChat(MessageCreateDTO messageCreateDTO) {
        // Logic to add user to chat and broadcast a welcome message
        User user = userRepository.findById(messageCreateDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with this id: " + messageCreateDTO.getUserId()));

        Chat chat = chatRepository.findById(messageCreateDTO.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat not found with this id : " + messageCreateDTO.getChatId()));

        // Add the user to the chat if they're not already a member
        if (!chat.getUsers().contains(user)) {
            chat.getUsers().add(user);
            user.getChats().add(chat);
            chatRepository.save(chat);
        }

        // Create a welcome message
        Message welcomeMessage = new Message();
        welcomeMessage.setContent("Welcome " + user.getUserName() + " to the chat!");
        welcomeMessage.setTimestamp(LocalDateTime.now());
        welcomeMessage.setChat(chat);
        welcomeMessage.setUser(user);

        // Save the welcome message
        messageRepository.save(welcomeMessage);

        // Convert to MessageDTO
        MessageDTO messageDTO = MessageMapper.INSTANCE.mapperMessageToDto(welcomeMessage);

        // Broadcast via WebSocket
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getChatId(), messageDTO);

        return messageDTO;
    }

}
