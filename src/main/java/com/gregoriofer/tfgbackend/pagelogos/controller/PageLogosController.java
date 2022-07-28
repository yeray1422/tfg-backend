package com.gregoriofer.tfgbackend.pagelogos.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

// import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.gregoriofer.tfgbackend.pagelogos.model.PageLogos;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
public class PageLogosController {

    private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/page-logos");

    private static final String HEADER_API = "apikey";

    private String getApiKey(Map<String, String> headers) {
        String apiKey = headers.get(HEADER_API);
        return apiKey;
    }

    @GetMapping("/logos")
    public ResponseEntity<ArrayList<PageLogos>> getLogos(@RequestHeader Map<String, String> headers, @RequestParam(required = false) String page) {
        String apiKey = getApiKey(headers);
        Mono<PageLogos[]> monoResponse;
        if (page != null) {
            monoResponse = client.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("page", page).build())
                    .header(HEADER_API, apiKey)
                    .exchangeToMono(response -> {
                        if (response.statusCode().equals(HttpStatus.OK)) {
                            return response.bodyToMono(PageLogos[].class);
                        } else {
                            return Mono.just(null);
                        }
                    });
        } else {
            monoResponse = client.get()
                    .header(HEADER_API, apiKey)
                    .exchangeToMono(response -> {
                        if (response.statusCode().equals(HttpStatus.OK)) {
                            return response.bodyToMono(PageLogos[].class);
                        } else {
                            return Mono.just(null);
                        }
                    });
        }

        ResponseEntity<ArrayList<PageLogos>> response;
        ArrayList<PageLogos> pageLogosList = new ArrayList<>();
        try {
            PageLogos[] pageLogosArray = monoResponse.block();
            for (PageLogos pageLogos : pageLogosArray) {
                pageLogosList.add(pageLogos);
            }
            response = new ResponseEntity<ArrayList<PageLogos>>((ArrayList<PageLogos>) pageLogosList.stream().collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }
        
        return response;
    }

}
