package com.aman.chat_application.Mapper;

import com.aman.chat_application.Dto.Message.MessageCreateDTO;
import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message mapperMessage(MessageDTO messageDTO);

    MessageCreateDTO mapperMessageCreateDto(Message message);

}
