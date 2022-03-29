package com.telstra.codechallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import com.telstra.codechallenge.exception.CustomApiException;
import com.telstra.codechallenge.gitapi.model.GitResponseList;
import org.springframework.http.HttpStatus;


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

public class MicroServiceGitTest {

    @LocalServerPort
    private String port;

    @Value("${git.base.url}")
    private String externalApiUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSearchRepoSuccessfull() throws RestClientException {
        ResponseEntity<GitResponseList> response =
                restTemplate.getForEntity("http://localhost:" + port + "/git/starredrepos?count=" + 1, GitResponseList.class);
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSearchRepoWithDefaultCount() throws RestClientException {
            ResponseEntity<GitResponseList> response = restTemplate
                    .getForEntity("http://localhost:" + port + "/git/starredrepos?count=2", GitResponseList.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().getItems().size() > 1);
    }

    @Test
    public void testRequestWithNoInputParam() throws RestClientException {
        CustomApiException response = restTemplate
                .getForObject("http://localhost:" + port + "/git/starredrepos", CustomApiException.class);
        assertTrue(response.getStatus().equals(HttpStatus.BAD_REQUEST));

    }

}
