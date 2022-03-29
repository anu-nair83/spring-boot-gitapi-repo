package com.telstra.codechallenge.gitapi.configuration;

import com.telstra.codechallenge.gitapi.model.GitAPIParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class GitAPIParamConfig {

    /**
     * Declared an initial state bean for git API query params
     * @return
     */
    @Bean
    public GitAPIParams starredRepoDescInWeek() {
            Map<String, String> params = new HashMap<>();
            params.put("q", "created:%3E"+ LocalDate.now().minusDays(7).toString());
            params.put("sort","stars");
            params.put("order", "desc");
            params.put("page","1");
            return new GitAPIParams(params);
    }
}
