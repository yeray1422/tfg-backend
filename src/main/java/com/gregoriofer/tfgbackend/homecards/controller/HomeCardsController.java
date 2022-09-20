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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@Tag(name = "Home Cards", description = "Home Cards API")
public class HomeCardsController {
    
    private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/home-cards");

    private static final String HEADER_API = "apikey";

    private String getApiKey(Map<String, String> headers) {
        String apiKey = headers.get(HEADER_API);
        return apiKey;
    }

    @Operation(description = "Operation to fetch home cards", method = "get", tags = {"Home Cards"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HomeCards.class)))),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HomeCards.class))))
    })
    @GetMapping("/home-cards")
    public ResponseEntity<ArrayList<HomeCards>> getHomeCards(@Parameter(in = ParameterIn.HEADER, name = HEADER_API, required = true) @RequestHeader Map<String, String> headers) {
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
