package com.telstra.codechallenge.gitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitAPIResponse {
  	private String name;
	private String html_url;
	private String description;
	private Integer watchers_count;
	private String language;
}