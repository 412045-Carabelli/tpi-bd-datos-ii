package org.datosii.tpidatosii.controller;

import org.datosii.tpidatosii.dto.UserCreateDto;
import org.datosii.tpidatosii.model.User;
import org.datosii.tpidatosii.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userDto) {
        try {
            User user = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<?> followUser(@PathVariable Long followerId,
                                        @PathVariable Long followedId) {
        try {
            userService.followUser(followerId, followedId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long followerId,
                                          @PathVariable Long followedId) {
        userService.unfollowUser(followerId, followedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) {
        List<User> following = userService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long id) {
        List<User> followers = userService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<User>> getRecommendedUsers(@PathVariable Long id) {
        List<User> recommendations = userService.getRecommendedUsersByCommonLikes(id);
        return ResponseEntity.ok(recommendations);
    }
}
