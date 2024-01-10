package com.pedrodovale.dockeroauthproxy.resourceserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BooksController {

  @GetMapping("/books")
  public List<Book> getBooks(@RequestHeader(value = "Shelf-User") String user) {
    switch(user) {
      case "sherlock":
        return Arrays.asList(
                new Book("A Study in Scarlet", "Sir Arthur Conan Doyle"),
                new Book("The Sign of the Four", "Sir Arthur Conan Doyle"),
                new Book("The Final Problem", "Sir Arthur Conan Doyle"));
    }
    return Collections.emptyList();
  }
}
