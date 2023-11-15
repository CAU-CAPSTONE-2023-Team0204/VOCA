package com.chaejeom.chaejeom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class DjangoTestController {
    @PostMapping("/send-data-to-django")
    public String sendDataToDjango(@RequestBody String dataToSend){
        final String djangoURL = "http://localhost:8000/received-data";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(djangoURL, dataToSend, String.class);

        System.out.println("Response form Django : "+ response);

        return response;
    }
}
