package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {

     public MessageDTO saveMessage(MessageCreateDTO messageCreateDTO);

     public MessageDTO addUserToChat(MessageCreateDTO messageCreateDTO);
}
