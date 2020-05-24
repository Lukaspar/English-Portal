package com.lukaspar.ep.core.repository;

import com.lukaspar.ep.core.model.EnglishDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnglishDictionaryRepository extends JpaRepository<EnglishDictionary, Long> {

    @Query(value="SELECT english_word FROM english_dictionary ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<String> drawEnglishWord();

    Optional<EnglishDictionary> findByEnglishWord(String englishWord);

    boolean existsByEnglishWord(String englishWord);
}
