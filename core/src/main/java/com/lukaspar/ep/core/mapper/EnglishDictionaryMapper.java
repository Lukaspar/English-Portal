package com.lukaspar.ep.core.mapper;

import com.lukaspar.ep.core.dto.InputEnglishWordDto;
import com.lukaspar.ep.core.model.EnglishDictionary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnglishDictionaryMapper {

    @Mapping(target = "id", ignore = true)
    EnglishDictionary inputEnglishWordDtoToEnglishDictionary(InputEnglishWordDto request);
}
