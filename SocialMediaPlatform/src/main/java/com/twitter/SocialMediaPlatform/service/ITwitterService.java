package com.twitter.SocialMediaPlatform.service;

import com.twitter.SocialMediaPlatform.DTOs.*;
import com.twitter.SocialMediaPlatform.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ITwitterService {
    ResponseEntity<?> registerNewUser(SignupRequest request);
    ResponseEntity<?> findUserDetailByUserID(Long id);
    ResponseEntity<?> loginUser(LoginRequest request);
    ResponseEntity<?> createUserPost(PostRequest request);
    ResponseEntity<?> findPostDetailsByPostId(Long id);
    ResponseEntity<?> createPostComment(CommentRequest request);
    ResponseEntity<?> getAllAvailablePosts();
    ResponseEntity<?> editUserPost(PatchPostRequest request);
    ResponseEntity<?> deletePost(DeletePostRequest request);
    ResponseEntity<?> getCommentByCommentId(Long id);
    ResponseEntity<?> editUserComment(PatchCommentRequest request);
    ResponseEntity<?> deleteComment(DeleteCommentRequest request);
    ResponseEntity<?> getAllAvailableUsers();
}
