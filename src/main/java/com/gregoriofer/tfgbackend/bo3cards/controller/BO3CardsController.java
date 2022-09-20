package com.gregoriofer.tfgbackend.bo3cards.controller;

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

import com.gregoriofer.tfgbackend.bo3cards.model.BO3Cards;
import com.gregoriofer.tfgbackend.bo3cards.model.SortById;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@Tag(name = "Black Ops 3 Cards", description = "Black Ops 3 Cards API")
public class BO3CardsController {

  private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/bo3-cards");

  private static final String HEADER_API = "apikey";

  private String getApiKey(Map<String, String> headers) {
    String apiKey = headers.get(HEADER_API);
    return apiKey;
  }

  @Operation(description = "Operation to fetch Black Ops 3 cards", method = "get", tags = {"Black Ops 3 Cards"})
  @ApiResponses(value = {
    @ApiResponse(responseCode="200", description= "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BO3Cards.class)))),
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BO3Cards.class))))
  })
  @GetMapping("/bo3")
  public ResponseEntity<ArrayList<BO3Cards>> getBO3Cards (@Parameter(in = ParameterIn.HEADER, name = HEADER_API, required = true) @RequestHeader Map <String, String> headers) {
    String apiKey = getApiKey(headers);
    Mono<BO3Cards[]> monoResponse = client.get()
        .header(HEADER_API, apiKey)
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(BO3Cards[].class);
          } else {
            return Mono.just(null);
          }
        });

    ResponseEntity<ArrayList<BO3Cards>> response;
    ArrayList<BO3Cards> bo3CardsList = new ArrayList<>();
    try {
      BO3Cards[] bo3CardsArray = monoResponse.block();
      for (BO3Cards bo3Cards : bo3CardsArray) {
        bo3CardsList.add(bo3Cards);
      }
      Collections.sort(bo3CardsList, new SortById());
      response = new ResponseEntity<ArrayList<BO3Cards>>((ArrayList<BO3Cards>) bo3CardsList.stream().collect(Collectors.toList()), HttpStatus.OK);
    } catch (Exception e) {
      response = new ResponseEntity<ArrayList<BO3Cards>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    return response;
  }
}
