package com.oops.major.socialMediaPlatform.controller;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.service.CommentService;
import com.oops.major.socialMediaPlatform.util.AppUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentCreationRequestDto commentCreationRequestDto) {
        String response = commentService.addComment(commentCreationRequestDto);
        if (response.equals(AppConstant.COMMENT_CREATION_SUCCESSFUL)) {
            return ResponseEntity.ok(response);
        }
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(response);
        return ResponseEntity.ok(errorDto);
    }

    @GetMapping(value = "/comment")
    public ResponseEntity<?> getCommentById(@RequestParam(value = "commentID", required = true) final Integer commentID) {
        CommentDto commentDto = commentService.getCommentById(commentID);
        if (ObjectUtils.isEmpty(commentDto)) {
            String response = AppConstant.COMMENT_DOES_NOT_EXIST;
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(commentDto);
    }

    @PatchMapping(value = "/comment")
    public ResponseEntity<?> updateCommentById(@RequestBody CommentEditRequestDto commentEditRequestDto) {
        String response = commentService.editComment(commentEditRequestDto);
        if (response.equals(AppConstant.COMMENT_DOES_NOT_EXIST)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/comment")
    public ResponseEntity<?> deleteCommentById(@RequestParam(value = "commentID", required = true) final Integer commentID) {
        String response = commentService.deleteComment(commentID);
        if (response.equals(AppConstant.COMMENT_DOES_NOT_EXIST)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(response);
    }
}
