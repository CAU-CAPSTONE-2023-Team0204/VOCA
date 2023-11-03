package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.service.VocabService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vocab")
@RequiredArgsConstructor
public class VocabController {
    private final VocabService vocabService;


}
