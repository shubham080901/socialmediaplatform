package com.oops.major.socialMediaPlatform.service;

import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.model.Post;
import com.oops.major.socialMediaPlatform.model.Users;

import java.util.List;

public interface PostService {

    Post save(Post post);

    Post findPostById(int postId);

    String createPost(PostCreationRequestDto postCreationRequestDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(int postId);

    String deletePost(int postId);

    String editPost(PostEditRequestDto postEditRequestDto);

}
