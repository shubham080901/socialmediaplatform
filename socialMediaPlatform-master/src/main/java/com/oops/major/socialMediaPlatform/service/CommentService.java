package com.oops.major.socialMediaPlatform.service;

import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.model.Comment;
import com.oops.major.socialMediaPlatform.model.Users;

public interface CommentService {

    Comment save(Comment comment);

    Comment findCommentById(int commentId);

    String addComment(CommentCreationRequestDto commentCreationRequestDto);

    String deleteComment(int commentID);

    CommentDto getCommentById(int commentId);

    String editComment(CommentEditRequestDto commentEditRequestDto);
}
