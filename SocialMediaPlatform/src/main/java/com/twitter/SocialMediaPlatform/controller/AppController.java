package com.twitter.SocialMediaPlatform.controller;

import com.twitter.SocialMediaPlatform.DTOs.*;
import com.twitter.SocialMediaPlatform.service.ITwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final ITwitterService service;

    /* ------------------- Twitter User Controllers ----------------- */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignupRequest request) {
        return service.registerNewUser(request);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request) {
        return service.loginUser(request);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@Validated @RequestParam Long id) {
        return service.findUserDetailByUserID(id);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllTheUsers() {
        return service.getAllAvailableUsers();
    }

    /* ------------------- User Posts ----------------- */
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@Validated @RequestBody PostRequest request) {
        return service.createUserPost(request);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@Validated @RequestParam Long postId) {
        return service.findPostDetailsByPostId(postId);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllThePosts() {
        return service.getAllAvailablePosts();
    }

    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@Validated @RequestBody PatchPostRequest request) {
        return service.editUserPost(request);
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@Validated @RequestBody DeletePostRequest request) {
        return service.deletePost(request);
    }

    /* ------------------- User Posts Comments ----------------- */
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@Validated @RequestBody CommentRequest request) {
        return service.createPostComment(request);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentById(@Validated @RequestParam Long id) {
        return service.getCommentByCommentId(id);
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> editComment(@Validated @RequestBody PatchCommentRequest request) {
        return service.editUserComment(request);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@Validated @RequestBody DeleteCommentRequest request) {
        return service.deleteComment(request);
    }
}

