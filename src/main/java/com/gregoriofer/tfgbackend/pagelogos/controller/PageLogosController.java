package com.gregoriofer.tfgbackend.pagelogos.controller;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.gregoriofer.tfgbackend.pagelogos.model.PageLogos;
import com.gregoriofer.tfgbackend.pagelogos.utils.JSONTransformer;

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
    public ResponseEntity<ArrayList<PageLogos>> getLogos(@RequestHeader Map<String, String> headers) {
        String apiKey = getApiKey(headers);
        Mono<JSONArray> monoResponse = client.get()
                .header(HEADER_API, apiKey)
                .exchangeToMono((response) -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(JSONArray.class);
                    } else {
                        return Mono.just(null);
                    }
                });

        ResponseEntity<ArrayList<PageLogos>> response;
        ArrayList<PageLogos> pageLogosList = new ArrayList<>();
        try {
            JSONArray responseBody = monoResponse.block();
            pageLogosList = JSONTransformer.toPageLogosList(responseBody);
            response = new ResponseEntity<ArrayList<PageLogos>>(pageLogosList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<ArrayList<PageLogos>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
