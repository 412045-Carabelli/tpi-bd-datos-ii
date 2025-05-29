package org.datosii.tpidatosii.controller;

import org.datosii.tpidatosii.dto.PostCreateDto;
import org.datosii.tpidatosii.model.Post;
import org.datosii.tpidatosii.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostCreateDto postDto) {
        try {
            Post post = postService.createPost(postDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<Post>> getFeedForUser(@PathVariable Long userId) {
        List<Post> feed = postService.getFeedForUser(userId);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.likePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/like/{userId}")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.unlikePost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommendations/hashtags/{userId}")
    public ResponseEntity<List<Post>> getRecommendedPostsByHashtags(@PathVariable Long userId) {
        List<Post> recommendations = postService.getRecommendedPostsByHashtags(userId);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/recommendations/affinity/{userId}")
    public ResponseEntity<List<Post>> getRecommendedPostsByUserAffinity(@PathVariable Long userId) {
        List<Post> recommendations = postService.getRecommendedPostsByUserAffinity(userId);
        return ResponseEntity.ok(recommendations);
    }
}