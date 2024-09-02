package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.Chat.ChatCreateDTO;
import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Chat.ChatUpdateDTO;
import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Exception.ChatNotFoundException;
import com.aman.chat_application.Exception.UserNotFoundException;
import com.aman.chat_application.Mapper.ChatMapper;
import com.aman.chat_application.Mapper.MessageMapper;
import com.aman.chat_application.Model.Chat;
import com.aman.chat_application.Model.Message;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.ChatRepository;
import com.aman.chat_application.Repository.MessageRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.ChatService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public MessageDTO saveMessage(MessageCreateDTO messageCreateDTO) {
        Chat chat = chatRepository.findById(messageCreateDTO.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));

        User user = userRepository.findById(messageCreateDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Create and save the message
        Message message = MessageMapper.INSTANCE.mapperMessageFromCreate(messageCreateDTO);
        message.setChat(chat);
        message.setUser(user);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        // Convert to DTO and send via WebSocket
        MessageDTO messageDTO = MessageMapper.INSTANCE.mapperMessageToDto(message);
        messagingTemplate.convertAndSend("/topic/chat/" + chat.getChatId(), messageDTO);

        return messageDTO;
    }

    @Override
    public MessageDTO addUserToChat(MessageCreateDTO messageCreateDTO) {
        Chat chat = chatRepository.findById(messageCreateDTO.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));

        User user = userRepository.findById(messageCreateDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Add user to the chat if not already added
        Message welcomeMessage = null;
        if (!chat.getUsers().contains(user)) {
            chat.getUsers().add(user);
            chatRepository.save(chat);

            // Create a welcome message
            welcomeMessage = new Message();
            welcomeMessage.setContent("Welcome " + user.getUserName() + " to the chat!");
            welcomeMessage.setTimestamp(LocalDateTime.now());
            welcomeMessage.setChat(chat);
            welcomeMessage.setUser(user);
            messageRepository.save(welcomeMessage);

            // Convert to DTO and send via WebSocket
            MessageDTO welcomeMessageDTO = MessageMapper.INSTANCE.mapperMessageToDto(welcomeMessage);
            messagingTemplate.convertAndSend("/topic/chat/" + chat.getChatId(), welcomeMessageDTO);
        }

        return MessageMapper.INSTANCE.mapperMessageToDto(welcomeMessage);
    }

    @Override
    public ChatDTO getChatDetails(Integer chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        return ChatMapper.INSTANCE.mapperChatDto(chat);
    }

    @Override
    public ChatDTO createChat(ChatCreateDTO chatCreateDTO) {
        Chat chat = ChatMapper.INSTANCE.mapperChatDtotoDto(chatCreateDTO);
        chat = chatRepository.save(chat);
        return ChatMapper.INSTANCE.mapperChatDto(chat);
    }

    @Override
    public void deleteChat(Integer chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        chatRepository.delete(chat);
    }

    @Override
    public ChatDTO updateChat(Integer chatId, ChatUpdateDTO chatUpdateDTO) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        chat.setChatName(chatUpdateDTO.getChatName());
        chat = chatRepository.save(chat);
        return ChatMapper.INSTANCE.mapperChatDto(chat);
    }

    @Override
    public List<ChatDTO> getUserChats(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getChats().stream()
                .map(ChatMapper.INSTANCE::mapperChatDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getMessagesInChat(Integer chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        return chat.getMessages().stream()
                .map(MessageMapper.INSTANCE::mapperMessageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> getUserActiveChats(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getChats().stream()
                .filter(chat -> chat.getUsers().contains(user))
                .map(ChatMapper.INSTANCE::mapperChatDto)
                .collect(Collectors.toList());
    }

    @Override
    public void leaveChat(Integer chatId, Integer userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Remove the user from the chat
        if (chat.getUsers().contains(user)) {
            chat.getUsers().remove(user);
            chatRepository.save(chat);

            // Optionally, notify other users about the user leaving
            String notificationMessage = user.getUserName() + " has left the chat.";
            Message notification = new Message();
            notification.setContent(notificationMessage);
            notification.setTimestamp(LocalDateTime.now());
            notification.setChat(chat);
            notification.setUser(user);
            messageRepository.save(notification);

            // Convert to DTO and send via WebSocket
            MessageDTO notificationDTO = MessageMapper.INSTANCE.mapperMessageToDto(notification);
            messagingTemplate.convertAndSend("/topic/chat/" + chat.getChatId(), notificationDTO);
        } else {
            throw new UserNotFoundException("User is not part of the chat");
        }
    }

    @Override
    public List<ChatDTO> searchChats(String name) {
        List<Chat> chats = chatRepository.findByChatNameContainingIgnoreCase(name);
        return chats.stream()
                .map(ChatMapper.INSTANCE::mapperChatDto)
                .collect(Collectors.toList());
    }
}
