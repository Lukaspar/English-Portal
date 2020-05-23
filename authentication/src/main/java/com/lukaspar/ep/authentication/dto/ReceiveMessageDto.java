package com.lukaspar.ep.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessageDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String sender;
    @NotNull
    private LocalDateTime messageTime;
}
