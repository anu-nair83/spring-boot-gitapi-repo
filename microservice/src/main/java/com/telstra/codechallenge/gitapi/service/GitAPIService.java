package com.telstra.codechallenge.gitapi.service;

import com.telstra.codechallenge.gitapi.model.GitAPIParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.net.URI;
import java.util.Map;

import com.telstra.codechallenge.gitapi.model.GitResponseList;

@Service
public class GitAPIService {

  @Value("${git.base.url}")
  private String gitBaseUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private GitAPIParams starredRepoDescInWeek;


  /**
   * Returns an array of starred git repositories.
   * Get API params by calling gitQueryParams
   * @return - a quote array
   */
  @CircuitBreaker(name = "default", fallbackMethod = "getDefaultGitAPIResponse")
  public ResponseEntity<GitResponseList> getStarredRepositories(Integer resultCount) {
    final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(gitBaseUrl+"/search/repositories");
    getQueryParams().forEach((paramName, paramValue) -> builder.queryParam(paramName, paramValue));

    //Set custom parameter received from API call
    builder.queryParam("per_page", resultCount.toString());
    ResponseEntity<GitResponseList> originalGitResponse = restTemplate.getForEntity((builder.build(true).toUri()), GitResponseList.class);
    return new ResponseEntity<>(originalGitResponse.getBody(), originalGitResponse.getStatusCode());

  }


  /**
   * This is the fallback method called by circuit breaker if call to git API returns error
   * We return a default response, that currently displays the error
   * @param ex - Exception that caused circuit breaker to fallback on this method
   * @return GitResponseList - default response
   */
  public ResponseEntity<GitResponseList> getDefaultGitAPIResponse(Exception ex) {
    GitResponseList errorResponseList = new GitResponseList();
    System.out.println("Circuit breaker called default git api response:"+ex.getMessage());
    errorResponseList.setItems(GitResponseList.getDefaultGitAPIResponse(ex.getMessage()));
    ResponseEntity<GitResponseList> response = new ResponseEntity<GitResponseList>(errorResponseList, HttpStatus.OK);
    return response;
  }

  /**
   * Method to return query params for API call
   * @return
   */
  public Map<String, String> getQueryParams() {
    return starredRepoDescInWeek.getParams();
  }
}


