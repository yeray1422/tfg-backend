package com.gregoriofer.tfgbackend.comments.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.gregoriofer.tfgbackend.comments.model.Comments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@Tag(name = "Users Comments", description = "Users Comments API")
public class CommentsController {

  private WebClient client = WebClient.create("https://ojemuehslwnravrhjgbk.supabase.co/rest/v1/comments");

  private static final String HEADER_API = "apikey";

  private String getApiKey(Map<String, String> headers) {
    String apikey = headers.get(HEADER_API);
    return apikey;
  }

  @Operation(description = "Operation to insert comments from the users", method = "POST", tags = { "Users Comments" })
  @PostMapping("/comments")
  public ResponseEntity<String> postComment(
      @Parameter(in = ParameterIn.HEADER, name = HEADER_API, required = true) @RequestHeader Map<String, String> headers,
      @RequestBody(description = "Comment to add", required = true, content = @Content(schema = @Schema(implementation = Comments.class))) @org.springframework.web.bind.annotation.RequestBody Comments comments) {
    String apikey = getApiKey(headers);
    Mono<Comments> monoResponse = client.post()
          .header(HEADER_API, apikey)
          .body(BodyInserters.fromValue(comments.toMap()))
          .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.CREATED)) {
              return response.bodyToMono(Comments.class);
            } else {
              return Mono.just(null);
            }
          });
      
    ResponseEntity<String> response;
    try {
      monoResponse.block();
      response = new ResponseEntity<>("Inserted correctly", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      response = new ResponseEntity<>("Something went wrong...", HttpStatus.UNAUTHORIZED);
    }

    return response;
  }
}
