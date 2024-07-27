package com.oops.major.socialMediaPlatform.controller;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.service.PostService;
import com.oops.major.socialMediaPlatform.util.AppUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping(value = "/post")
    public ResponseEntity<?> createPost(@RequestBody PostCreationRequestDto postCreationRequestDto) {
        String response = postService.createPost(postCreationRequestDto);
        if (response.equals(AppConstant.USER_DOES_NOT_EXIST)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/post")
    public ResponseEntity<?> getPostById(@RequestParam(value = "postID", required = true) final Integer postID) {
        PostDto postDto = postService.getPostById(postID);
        if (ObjectUtils.isEmpty(postDto)) {
            String response = AppConstant.POST_DOES_NOT_EXIST;
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(postDto);
    }

    @PatchMapping(value = "/post")
    public ResponseEntity<?> updatePostById(@RequestBody PostEditRequestDto postEditRequestDto) {
        String response = postService.editPost(postEditRequestDto);
        if (response.equals(AppConstant.POST_DOES_NOT_EXIST)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/post")
    public ResponseEntity<?> deletePostById(@RequestParam(value = "postID", required = true) final Integer postID) {
        String response = postService.deletePost(postID);
        if (response.equals(AppConstant.POST_DOES_NOT_EXIST)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
