package com.lukaspar.ep.core.service;

import com.lukaspar.ep.core.dto.InputEnglishWordDto;
import com.lukaspar.ep.core.dto.TranslateWordResponseDto;
import com.lukaspar.ep.core.exception.EnglishDictionaryAlreadyExistsException;
import com.lukaspar.ep.core.exception.EnglishDictionaryIsEmptyException;
import com.lukaspar.ep.core.exception.EnglishDictionaryNotFoundException;
import com.lukaspar.ep.core.mapper.EnglishDictionaryMapper;
import com.lukaspar.ep.core.model.EnglishDictionary;
import com.lukaspar.ep.core.repository.EnglishDictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final EnglishDictionaryRepository englishDictionaryRepository;
    private final EnglishDictionaryMapper englishDictionaryMapper;

    public String generateRandomEnglishWord() {
        return englishDictionaryRepository.drawEnglishWord().orElseThrow(EnglishDictionaryIsEmptyException::new);
    }

    public TranslateWordResponseDto translateEnglishWord(InputEnglishWordDto request) {
        EnglishDictionary englishDictionary = englishDictionaryRepository
                .findByEnglishWord(request.getEnglishWord())
                .orElseThrow(() -> new EnglishDictionaryNotFoundException(request.getEnglishWord()));

        return TranslateWordResponseDto.builder()
                .messageResult(calculateMessageResult(request.getPolishTranscription(), englishDictionary.getPolishTranscription()))
                .inputEnglishWord(request.getEnglishWord())
                .inputPolishTranscription(request.getPolishTranscription())
                .correctPolishTranscription(englishDictionary.getPolishTranscription())
                .build();
    }

    private String calculateMessageResult(String inputPolishTranscription, String correctPolishTranscription){
        if(Collections.disjoint(splitPolishTranscription(correctPolishTranscription), splitPolishTranscription(inputPolishTranscription))){
            return "Input polish transcription is incorrect!";
        }
        return "Congratulations! At least one polish transcription is correct.";
    }

    private List<String> splitPolishTranscription(String polishTranscription){
        return Arrays.asList(polishTranscription.toLowerCase().trim().split("\\s*,\\s*"));
    }

    @Transactional
    public EnglishDictionary addEnglishWord(InputEnglishWordDto request) {
        if (englishDictionaryRepository.existsByEnglishWord(request.getEnglishWord())) {
            throw new EnglishDictionaryAlreadyExistsException(request.getEnglishWord());
        }
        EnglishDictionary englishDictionary = englishDictionaryMapper.inputEnglishWordDtoToEnglishDictionary(request);
        return englishDictionaryRepository.saveAndFlush(englishDictionary);
    }

    @Transactional
    public void deleteEnglishWord(InputEnglishWordDto request) {
        EnglishDictionary englishDictionary = englishDictionaryRepository
                .findByEnglishWord(request.getEnglishWord())
                .orElseThrow(() -> new EnglishDictionaryNotFoundException(request.getEnglishWord()));

        englishDictionaryRepository.delete(englishDictionary);
    }

    public Page<EnglishDictionary> englishDictionary(Pageable pageable) {
        return englishDictionaryRepository.findAll(pageable);
    }
}
