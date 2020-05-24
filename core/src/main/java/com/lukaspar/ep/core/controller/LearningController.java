package com.lukaspar.ep.core.controller;

import com.lukaspar.ep.core.dto.InputEnglishWordDto;
import com.lukaspar.ep.core.dto.TranslateWordResponseDto;
import com.lukaspar.ep.core.model.EnglishDictionary;
import com.lukaspar.ep.core.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core-module")
public class LearningController {

    private final LearningService learningService;

    @GetMapping("draw-english-word")
    public ResponseEntity<String> generateRandomEnglishWord(){
        return ResponseEntity.ok(learningService.generateRandomEnglishWord());
    }

    @PostMapping("translate-english-word")
    public ResponseEntity<TranslateWordResponseDto> translateEnglishWord(@Valid @RequestBody InputEnglishWordDto inputEnglishWordDto){
        return ResponseEntity.ok(learningService.translateEnglishWord(inputEnglishWordDto));
    }

    @PostMapping("admin/add-english-word")
    public ResponseEntity<Long> addEnglishWord(@Valid @RequestBody InputEnglishWordDto inputEnglishWordDto){
        EnglishDictionary englishDictionary = learningService.addEnglishWord(inputEnglishWordDto);
        return ResponseEntity.status(CREATED).body(englishDictionary.getId());

    }

    @DeleteMapping("admin/delete-english-word")
    public ResponseEntity<Long> deleteEnglishWord(@Valid @RequestBody InputEnglishWordDto inputEnglishWordDto){
        learningService.deleteEnglishWord(inputEnglishWordDto);
        return ResponseEntity.noContent().build();
    }

}
