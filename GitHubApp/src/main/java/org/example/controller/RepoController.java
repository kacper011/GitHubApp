package org.example.controller;

import org.example.service.GitHubService;
import org.example.model.RepoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class RepoController {

    private final GitHubService gitHubService;

    public RepoController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<?> getOriginalRepositories(@PathVariable String username) {
        try {
            List<RepoResponse> result = gitHubService.getOriginalRepos(username);
            return ResponseEntity.ok(result);
        } catch (GitHubService.UserNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "message", "User not found"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", 500,
                    "message", "Internal server error"
            ));
        }
    }
}
