package com.lukaspar.ep.authentication.service;

import com.lukaspar.ep.authentication.dto.ReceiveMessageDto;
import com.lukaspar.ep.authentication.dto.SendMessageDto;
import com.lukaspar.ep.authentication.exception.MessageReceiverIdenticalLikeSenderException;
import com.lukaspar.ep.authentication.exception.MessageReceiverMustBeSenderFriendException;
import com.lukaspar.ep.authentication.exception.UserNotFoundException;
import com.lukaspar.ep.authentication.mapper.MessageMapper;
import com.lukaspar.ep.authentication.model.Message;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.repository.MessageRepository;
import com.lukaspar.ep.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    @Transactional
    public Message sendMessage(SendMessageDto sendMessageDto, String sender) {
        User receiver = userRepository.findByUsername(sendMessageDto.getReceiver()).orElseThrow(() -> new UserNotFoundException(sendMessageDto.getReceiver()));

        if(receiver.getUsername().equals(sender)){
            throw new MessageReceiverIdenticalLikeSenderException();
        }

        if(!receiver.getAllFriends().contains(sender)){
            throw new MessageReceiverMustBeSenderFriendException();
        }

        Message message = messageMapper.sendMessageDtoToMessage(sendMessageDto);
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setMessageTime(LocalDateTime.now());
        receiver.addMessage(message);
        return messageRepository.saveAndFlush(message);
    }

    public List<ReceiveMessageDto> retrieveMessages(String username) {
        User loggedUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Message> userMessages = messageRepository.findByReceiver(loggedUser);
        return messageMapper.messageListToReceiveMessageDtoList(userMessages);
    }
}
