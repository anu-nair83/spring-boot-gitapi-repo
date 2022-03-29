package com.telstra.codechallenge.quotes;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootQuotesController {

  private SpringBootQuotesService springBootQuotesService;

  public SpringBootQuotesController(
      SpringBootQuotesService springBootQuotesService) {
    this.springBootQuotesService = springBootQuotesService;
  }

  @RequestMapping(path = "/quotes", method = RequestMethod.GET)
  @TimeLimiter(name="default")
  public CompletableFuture<List<Quote>> quotes() {
      return CompletableFuture.supplyAsync(() -> Arrays.asList(springBootQuotesService.getQuotes()));
  }

  @RequestMapping(path = "/quotes/random", method = RequestMethod.GET)
  @TimeLimiter(name="default")
  public CompletableFuture<Quote> quote() {
    return CompletableFuture.supplyAsync(() -> {
      return springBootQuotesService.getRandomQuote();
    });
  }
}
