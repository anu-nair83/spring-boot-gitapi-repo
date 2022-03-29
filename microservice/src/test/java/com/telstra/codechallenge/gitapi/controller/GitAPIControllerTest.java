package com.telstra.codechallenge.gitapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.telstra.codechallenge.gitapi.controller.GitAPIController;
import com.telstra.codechallenge.gitapi.service.GitAPIService;
import com.telstra.codechallenge.gitapi.model.GitResponseList;
import com.telstra.codechallenge.gitapi.model.GitAPIResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(GitAPIController.class)
public class GitAPIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GitAPIService gitService;

    @Test
    public void testStarredLatestRepositories() throws Exception {
        GitResponseList response = new GitResponseList();
        response.setItems(GitResponseList.getDefaultGitAPIResponse("test"));
        Mockito.when(gitService.getStarredRepositories(1)).thenReturn(ResponseEntity.status(200).body(response));
        mockMvc.perform(get("/git/starredrepos?count=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("default"));
    }


    @Test
    public void testStarredLatestRepositoriesError() throws Exception {
        GitResponseList response = new GitResponseList();
        response.setItems(GitResponseList.getDefaultGitAPIResponse("test"));
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("sort","stars");
        queryParams.put("order", "desc");
        Mockito.when(gitService.getQueryParams()).thenReturn(queryParams);
        mockMvc.perform(get("/git/starredrepos?count=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
