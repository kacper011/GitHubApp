package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.BranchResponse;
import org.example.model.RepoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String GITHUB_API = "https://api.github.com";

    public List<RepoResponse> getOriginalRepos(String username) throws UserNotFoundException, JsonProcessingException {
        String repoUrl = GITHUB_API + "/users/" + username + "/repos";
        JsonNode repos;

        try {
            repos = mapper.readTree(restTemplate.getForObject(repoUrl, String.class));
        } catch (Exception e) {
            throw new UserNotFoundException();
        }

        List<RepoResponse> response = new ArrayList<>();

        for (JsonNode repo : repos) {
            if (!repo.get("fork").asBoolean()) {
                String nameRepo = repo.get("name").asText();
                String login = repo.get("owner").get("login").asText();

                String branchesUrl = GITHUB_API + "/repos/" + login + "/" + nameRepo + "/branches";
                JsonNode branches = mapper.readTree(restTemplate.getForObject(branchesUrl, String.class));

                List<BranchResponse> branchResponseList = new ArrayList<>();
                for (JsonNode branch : branches) {
                    String nameBranch = branch.get("name").asText();
                    String commitHash = branch.get("commit").get("sha").asText();
                    branchResponseList.add(new BranchResponse(nameBranch, commitHash));
                }

                response.add(new RepoResponse(nameRepo, login, branchResponseList));
            }
        }
        return response;
    }

    public static class UserNotFoundException extends Exception {
    }
}
