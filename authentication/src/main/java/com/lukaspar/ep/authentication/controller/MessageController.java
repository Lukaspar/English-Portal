package com.lukaspar.ep.authentication.controller;

import com.lukaspar.ep.authentication.dto.ReceiveMessageDto;
import com.lukaspar.ep.authentication.dto.SendMessageDto;
import com.lukaspar.ep.authentication.model.Message;
import com.lukaspar.ep.authentication.service.MessageService;
import com.lukaspar.ep.common.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication-module/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("send-message")
    public ResponseEntity<Long> sendMessage(@Valid @RequestBody SendMessageDto sendMessageDto, @AuthenticationPrincipal UserPrincipal principal){
        Message message = messageService.sendMessage(sendMessageDto, principal.getUsername());
        return ResponseEntity.status(CREATED).body(message.getId());
    }

    @GetMapping("retrieve-messages")
    public ResponseEntity<List<ReceiveMessageDto>> retrieveMessages(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(messageService.retrieveMessages(principal.getUsername()));
    }
}
