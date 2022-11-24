package com.gregoriofer.tfgbackend.soe.controller;

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

import com.gregoriofer.tfgbackend.soe.model.SoE;
import com.gregoriofer.tfgbackend.soe.model.SortById;

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
@Tag(name = "Shadows of Evil", description = "Shadows of Evil API")
public class SoEController {

  private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/shadows-of-evil");

  private static final String HEADER_API = "apikey";

  private String getApiKey(Map<String, String> headers) {
    String apiKey = headers.get(HEADER_API);
    return apiKey;
  }

  @Operation(description = "Operation to fetch Shadows of Evil stuff", method = "get", tags = { "Shadows of Evil" })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SoE.class)))),
      @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SoE.class))))
  })
  @GetMapping("/shadows-of-evil")
  public ResponseEntity<ArrayList<SoE>> getSoE(
      @Parameter(in = ParameterIn.HEADER, name = HEADER_API, required = true) @RequestHeader Map<String, String> headers) {
    String apiKey = getApiKey(headers);
    Mono<SoE[]> monoResponse = client.get()
        .header(HEADER_API, apiKey)
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(SoE[].class);
          } else {
            return Mono.just(null);
          }
        });

    ResponseEntity<ArrayList<SoE>> response;
    ArrayList<SoE> soeList = new ArrayList<>();
    try {
      SoE[] soeArray = monoResponse.block();
      for (SoE soe : soeArray) {
        soeList.add(soe);
      }
      Collections.sort(soeList, new SortById());
      response = new ResponseEntity<ArrayList<SoE>>((ArrayList<SoE>) soeList.stream().collect(Collectors.toList()),
          HttpStatus.OK);
    } catch (Exception e) {
      response = new ResponseEntity<ArrayList<SoE>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    return response;
  }
}
