package com.aman.chat_application.Mapper;

import com.aman.chat_application.Dto.Chat.ChatCreateDTO;
import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Chat.ChatUpdateDTO;
import com.aman.chat_application.Model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    Chat mapperChat(ChatDTO chatDTO);

    ChatDTO mapperChatDto(Chat chat);

    ChatCreateDTO mapperChatCreateDto(Chat chat);

    ChatUpdateDTO mapperChatUpdateDto(Chat chat);
}
