package com.pedrodovale.dockeroauthproxy.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
@RequiredArgsConstructor
public class BookshelfController {

  private final WebClient webClient;

  @Value("${resource-server.url.books}")
  private String resourceServerBooksUrl;

  @GetMapping("/")
  public String index(
      @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient,
      Model model) {
    model.addAttribute("username", authorizedClient.getPrincipalName());
    return "index";
  }

  @GetMapping("/books")
  public String getBooks(
      @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient,
      Model model) {
    model.addAttribute("username", authorizedClient.getPrincipalName());
    Book[] books =
        webClient
            .get()
            .uri(resourceServerBooksUrl)
            .attributes(oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .bodyToMono(Book[].class)
            .block();
    model.addAttribute("books", books);
    return "books";
  }
}
