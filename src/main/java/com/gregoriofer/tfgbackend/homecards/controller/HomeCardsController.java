package com.gregoriofer.tfgbackend.homecards.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.gregoriofer.tfgbackend.homecards.model.HomeCards;
import com.gregoriofer.tfgbackend.homecards.model.SortById;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
public class HomeCardsController {
    
    private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/home-cards");

    private static final String HEADER_API = "apikey";

    private String getApiKey(Map<String, String> headers) {
        String apiKey = headers.get(HEADER_API);
        return apiKey;
    }

    @GetMapping("/home-cards")
    public ResponseEntity<ArrayList<HomeCards>> getHomeCards(@RequestHeader Map<String, String> headers) {
        String apiKey = getApiKey(headers);
        Mono<HomeCards[]> monoResponse = client.get()
                .header(HEADER_API, apiKey)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(HomeCards[].class);
                    } else {
                        return Mono.just(null);
                    }
                });

        ResponseEntity<ArrayList<HomeCards>> response;
        ArrayList<HomeCards> homeCardsList = new ArrayList<>();
        try {
            HomeCards[] homeCardsArray = monoResponse.block();
            for (HomeCards homeCards : homeCardsArray) {
                homeCardsList.add(homeCards);
            }
            Collections.sort(homeCardsList, new SortById());
            response = new ResponseEntity<ArrayList<HomeCards>>((ArrayList<HomeCards>) homeCardsList.stream().collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<ArrayList<HomeCards>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
