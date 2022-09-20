package com.gregoriofer.tfgbackend.pagelogos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.gregoriofer.tfgbackend.pagelogos.model.PageLogos;
import com.gregoriofer.tfgbackend.pagelogos.model.SortById;

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
@Tag(name = "Page Logos", description = "Page Logos API")
public class PageLogosController {

    private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/page-logos");

    private static final String HEADER_API = "apikey";

    private String getApiKey(Map<String, String> headers) {
        String apiKey = headers.get(HEADER_API);
        return apiKey;
    }

    @Operation(description = "Operation to fetch logos of the pages", method = "get", tags = {"Page Logos"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PageLogos.class)))),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PageLogos.class))))})
    @GetMapping("/logos")
    public ResponseEntity<ArrayList<PageLogos>> getLogos(@Parameter(in = ParameterIn.HEADER, name = HEADER_API, required = true) @RequestHeader Map<String, String> headers, @Parameter(in = ParameterIn.QUERY, description = "GET a specific page => 'eq.{pagename}'.</br> GET all => empty", example = "") @RequestParam(required = false) String page) {
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
            Collections.sort(pageLogosList, new SortById());
            response = new ResponseEntity<ArrayList<PageLogos>>((ArrayList<PageLogos>) pageLogosList.stream().collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }
        
        return response;
    }

}
