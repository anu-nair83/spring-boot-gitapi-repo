package com.telstra.codechallenge.gitapi.model;

import java.util.*;

import lombok.Data;

@Data
public class GitResponseList {
    private List<GitAPIResponse> items;

    /**
     * Method defines default response
     * @param message
     * @return
     */
    public static List<GitAPIResponse> getDefaultGitAPIResponse(String message) {
        List<GitAPIResponse> responseList = new ArrayList<>();
        GitAPIResponse response = new GitAPIResponse();
        response.setName("default");
        response.setDescription(message);
        responseList.add(response);
        return responseList;
    }
}
