package com.lukaspar.ep.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputEnglishWordDto {
    @NotBlank
    private String englishWord;
    @NotBlank
    private String polishTranscription;
}
