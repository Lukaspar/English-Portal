package com.lukaspar.ep.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String receiver;
}
