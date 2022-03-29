package com.telstra.codechallenge.gitapi.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import com.telstra.codechallenge.gitapi.service.GitAPIService;
import com.telstra.codechallenge.gitapi.model.GitResponseList;
import com.telstra.codechallenge.gitapi.model.GitAPIResponse;
import com.telstra.codechallenge.gitapi.model.GitAPIParams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RunWith(MockitoJUnitRunner.class)
public class GitAPIServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    GitAPIParams starredRepoDescInWeek;

    @InjectMocks
    GitAPIService service;

    @Test
    public void testStarredLatestRepositoriesSuccess() throws Exception {
        ReflectionTestUtils.setField(service, "gitBaseUrl", "https://api.github.com");
        Mockito.when(
                restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(new GitResponseList()));
        ResponseEntity<GitResponseList> response = service.getStarredRepositories(1);
        verify(restTemplate, never()).getForEntity(Mockito.anyString(), Mockito.<Class<GitResponseList>>any());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testStarredLatestRepositoriesError() throws Exception {

        ReflectionTestUtils.setField(service, "gitBaseUrl", "https://api.github.com");
        Mockito.when(
                restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new GitResponseList()));
        ResponseEntity<GitResponseList> response = service.getStarredRepositories(1);
        verify(restTemplate, never()).getForEntity(Mockito.anyString(), Mockito.<Class<GitResponseList>>any());
        System.out.println(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testFallbackMethod() throws Exception {
        Exception ex = new Exception("Test message");
        ResponseEntity<GitResponseList> response = service.getDefaultGitAPIResponse(ex);
        Assert.assertTrue(response.getBody().getItems().size() == 1);
    }

}
