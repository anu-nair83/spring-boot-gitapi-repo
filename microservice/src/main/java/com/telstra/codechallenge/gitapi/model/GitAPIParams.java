package com.telstra.codechallenge.gitapi.model;

import lombok.Data;
import java.util.Map;

@Data
public class GitAPIParams {
    final Map<String, String> params;
}
