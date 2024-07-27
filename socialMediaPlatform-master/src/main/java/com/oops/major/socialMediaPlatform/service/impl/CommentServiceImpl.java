package com.oops.major.socialMediaPlatform.service.impl;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.model.Comment;
import com.oops.major.socialMediaPlatform.model.Post;
import com.oops.major.socialMediaPlatform.model.Users;
import com.oops.major.socialMediaPlatform.repository.CommentRepository;
import com.oops.major.socialMediaPlatform.repository.PostRepository;
import com.oops.major.socialMediaPlatform.repository.UserRepository;
import com.oops.major.socialMediaPlatform.service.CommentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(int commentId) {
        return commentRepository.findCommentById(commentId);
    }

    @Override
    public String addComment(CommentCreationRequestDto commentCreationRequestDto) {
        Users user = userRepository.findUserById(commentCreationRequestDto.getUserID());
        if (ObjectUtils.isEmpty(user)) {
            return AppConstant.USER_DOES_NOT_EXIST;
        }
        Post post = postRepository.findPostById(commentCreationRequestDto.getPostID());
        if (ObjectUtils.isEmpty(post)) {
            return AppConstant.POST_DOES_NOT_EXIST;
        }
        Comment comment = new Comment();
        comment.setCommentBody(commentCreationRequestDto.getCommentBody());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return AppConstant.COMMENT_CREATION_SUCCESSFUL;

    }

    @Override
    public String deleteComment(int commentId) {
        Comment comment = commentRepository.findCommentById(commentId);
        if (ObjectUtils.isEmpty(comment)) {
            return AppConstant.COMMENT_DOES_NOT_EXIST;
        }
        commentRepository.deleteById(commentId);
        return AppConstant.COMMENT_DELETED;
    }

    @Override
    public CommentDto getCommentById(int commentId) {
        CommentDto commentDto = null;
        Comment comment = commentRepository.findCommentById(commentId);
        if (ObjectUtils.isEmpty(comment)) {
            return null;
        }
        commentDto = new CommentDto();
        commentDto.setCommentID(comment.getId());
        commentDto.setCommentBody(comment.getCommentBody());

        CommentCreatorDto commentCreatorDto = new CommentCreatorDto();
        commentCreatorDto.setName(comment.getUser().getName());
        commentCreatorDto.setUserID(comment.getUser().getId());
        commentDto.setCommentCreator(commentCreatorDto);
        return commentDto;
    }

    @Override
    public String editComment(CommentEditRequestDto commentEditRequestDto) {
        Comment comment = commentRepository.findCommentById(commentEditRequestDto.getCommentID());
        if (ObjectUtils.isEmpty(comment)) {
            return AppConstant.COMMENT_DOES_NOT_EXIST;
        }
        comment.setCommentBody(commentEditRequestDto.getCommentBody());
        comment = commentRepository.save(comment);
        return AppConstant.COMMENT_EDITED_SUCCESSFULLY;
    }
}
