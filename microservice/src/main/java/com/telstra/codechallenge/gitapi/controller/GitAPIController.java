package com.telstra.codechallenge.gitapi.controller;

import com.telstra.codechallenge.gitapi.model.GitResponseList;
import com.telstra.codechallenge.gitapi.service.GitAPIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GitAPIController {

  @Autowired
  GitAPIService gitService;

  /**
   * Map get request to gitService
   * @param count
   * @return
   */
  @GetMapping(path = "/git/starredrepos", params="count", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GitResponseList> starredLatestRepositories(@RequestParam Integer count) {
    return gitService.getStarredRepositories(count);
  }

}


