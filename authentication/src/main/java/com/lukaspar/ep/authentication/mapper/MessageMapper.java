package com.lukaspar.ep.authentication.mapper;

import com.lukaspar.ep.authentication.dto.ReceiveMessageDto;
import com.lukaspar.ep.authentication.dto.SendMessageDto;
import com.lukaspar.ep.authentication.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "receiver", ignore = true)
    Message sendMessageDtoToMessage(SendMessageDto sendMessageDto);

    List<ReceiveMessageDto> messageListToReceiveMessageDtoList(List<Message> userMessages);
}
