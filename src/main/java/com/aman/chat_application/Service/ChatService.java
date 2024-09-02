package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.Chat.ChatCreateDTO;
import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Chat.ChatUpdateDTO;
import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

     MessageDTO saveMessage(MessageCreateDTO messageCreateDTO);

     MessageDTO addUserToChat(MessageCreateDTO messageCreateDTO);

     ChatDTO getChatDetails(Integer chatId);

     ChatDTO createChat(ChatCreateDTO chatCreateDTO);

     void deleteChat(Integer chatId);

     ChatDTO updateChat(Integer chatId, ChatUpdateDTO chatUpdateDTO);

     List<ChatDTO> getUserChats(Integer userId);

     List<MessageDTO> getMessagesInChat(Integer chatId);

     List<ChatDTO> getUserActiveChats(Integer userId);

     void leaveChat(Integer chatId, Integer userId);

     List<ChatDTO> searchChats(String name);
}
