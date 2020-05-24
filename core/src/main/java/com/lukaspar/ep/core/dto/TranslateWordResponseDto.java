package com.lukaspar.ep.core.dto;

import lombok.*;

@Getter
@Builder
public class TranslateWordResponseDto {
    private final String messageResult;
    private final String inputEnglishWord;
    private final String inputPolishTranscription;
    private final String correctPolishTranscription;
}
