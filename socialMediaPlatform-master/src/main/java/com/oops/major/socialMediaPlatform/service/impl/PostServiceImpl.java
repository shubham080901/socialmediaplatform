package com.oops.major.socialMediaPlatform.service.impl;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.model.Comment;
import com.oops.major.socialMediaPlatform.model.Post;
import com.oops.major.socialMediaPlatform.model.Users;
import com.oops.major.socialMediaPlatform.repository.CommentRepository;
import com.oops.major.socialMediaPlatform.repository.PostRepository;
import com.oops.major.socialMediaPlatform.repository.UserRepository;
import com.oops.major.socialMediaPlatform.service.PostService;
import com.oops.major.socialMediaPlatform.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findPostById(int postId) {
        return postRepository.findPostById(postId);
    }

    @Override
    public String createPost(PostCreationRequestDto postCreationRequestDto) {
        Users user = userRepository.findUserById(postCreationRequestDto.getUserID());
        if (ObjectUtils.isEmpty(user)) {
            return AppConstant.USER_DOES_NOT_EXIST;
        }
        Post post = new Post();
        post.setPostBody(postCreationRequestDto.getPostBody());
        post.setDate(new Date());
        post.setUser(user);
        post = postRepository.save(post);
        return AppConstant.POST_CREATION_SUCCESSFUL;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAllPosts();
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : postList) {
            List<Comment> commentList = commentRepository.findAllCommentsInAPost(post.getId());
            List<CommentDto> commentDtoList = getCommentDtoList(commentList);
            PostDto postDto = new PostDto();
            postDto.setPostID(post.getId());
            postDto.setPostBody(post.getPostBody());
            postDto.setDate(post.getDate());
            postDto.setComments(commentDtoList);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public PostDto getPostById(int postId) {
        PostDto postDto = null;
        Post post = postRepository.findPostById(postId);
        if (ObjectUtils.isEmpty(post)) {
            return null;
        }
        postDto = new PostDto();
        postDto.setPostID(post.getId());
        postDto.setPostBody(post.getPostBody());
        postDto.setDate(post.getDate());

        List<Comment> commentList = commentRepository.findAllCommentsInAPost(post.getId());
        List<CommentDto> commentDtoList = getCommentDtoList(commentList);
        postDto.setComments(commentDtoList);
        return postDto;
    }

    @Override
    public String deletePost(int postId) {
        Post post = postRepository.findPostById(postId);
        if (ObjectUtils.isEmpty(post)) {
            return AppConstant.POST_DOES_NOT_EXIST;
        }
        List<Comment> commentList = commentRepository.findAllCommentsInAPost(post.getId());
        if (ObjectUtils.isNotEmpty(commentList)) {
            for (Comment comment : commentList) {
                commentRepository.deleteById(comment.getId());
            }
        }
        postRepository.deleteById(postId);
        return AppConstant.POST_DELETED;
    }

    @Override
    public String editPost(PostEditRequestDto postEditRequestDto) {
        Post post = postRepository.findPostById(postEditRequestDto.getPostID());
        if (ObjectUtils.isEmpty(post)) {
            return AppConstant.POST_DOES_NOT_EXIST;
        }
        post.setPostBody(postEditRequestDto.getPostBody());
        post = postRepository.save(post);
        return AppConstant.POST_EDITED_SUCCESSFULLY;
    }

    private static List<CommentDto> getCommentDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentCreatorDto commentCreatorDto = new CommentCreatorDto();
            commentCreatorDto.setUserID(comment.getUser().getId());
            commentCreatorDto.setName(comment.getUser().getName());

            CommentDto commentDto = new CommentDto();
            commentDto.setCommentBody(comment.getCommentBody());
            commentDto.setCommentID(comment.getId());
            commentDto.setCommentCreator(commentCreatorDto);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

}
