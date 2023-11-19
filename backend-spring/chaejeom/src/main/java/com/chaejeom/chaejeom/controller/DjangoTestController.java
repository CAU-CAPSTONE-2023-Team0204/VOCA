package com.chaejeom.chaejeom.controller;

import com.chaejeom.chaejeom.dto.ScoringRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class DjangoTestController {
    @PostMapping("/api/django")
    public ScoringRequestDto sendDataToDjango(@RequestBody ScoringRequestDto request){
        final String djangoURL = "http://localhost:8000/VOCA/";

        RestTemplate restTemplate = new RestTemplate();
        ScoringRequestDto response = restTemplate.postForObject(djangoURL, request, ScoringRequestDto.class);

        System.out.println("Response form Django : ");

        return response;
    }
}
