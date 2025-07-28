package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenExistingUser_whenGetOriginalRepositories_thenReturnReposWithBranches() {
        // given
        String username = "octocat";

        // when
        ResponseEntity<List> response = restTemplate.getForEntity("/users/{username}/repos", List.class, username);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        List<?> repos = response.getBody();
        assertThat(repos).isNotEmpty();

        Map<String, Object> firstRepo = (Map<String, Object>) repos.get(0);
        assertThat(firstRepo).containsKeys("repoName", "login", "branches");

        List<Map<String, Object>> branches = (List<Map<String, Object>>) firstRepo.get("branches");
        assertThat(branches).isNotEmpty();

        Map<String, Object> firstBranch = branches.get(0);
        assertThat(firstBranch).containsKeys("name", "lastCommit");
    }
}
