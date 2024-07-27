package com.twitter.SocialMediaPlatform.service;

import com.twitter.SocialMediaPlatform.DTOs.*;
import com.twitter.SocialMediaPlatform.model.Comment;
import com.twitter.SocialMediaPlatform.model.Post;
import com.twitter.SocialMediaPlatform.model.User;
import com.twitter.SocialMediaPlatform.repository.ICommentRepository;
import com.twitter.SocialMediaPlatform.repository.IPostRepository;
import com.twitter.SocialMediaPlatform.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TwitterService implements ITwitterService{
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;

    /* ------------------- Twitter User ----------------- */

    @Transactional
    @Override
    public ResponseEntity<?> registerNewUser(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent() ||
                userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Forbidden, Account already exists.");
        } else {
            User user = User.builder()
                    .createdOn(LocalDateTime.now())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .build();
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful!");
        }
    }


    @Override
    public ResponseEntity<?> loginUser(LoginRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if(user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body("Login Successful");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
    }

    @Override
    public ResponseEntity<?> findUserDetailByUserID(Long id) {
        if(userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            List<PostResponse> postResponses = user.getPosts().stream().map(this::mapToPostResponse).toList();
            UserResponse response = UserResponse.builder()
                    .userId(user.getUserID())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .posts(postResponses)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
    }

    @Override
    public ResponseEntity<?> getAllAvailableUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }


        List<UserResponse> responses = allUsers.stream().map(this::mapToUserResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    private UserResponse mapToUserResponse(User user) {
        List<PostResponse> responses = user.getPosts().stream().map(this::mapToPostResponse).toList();
        return UserResponse.builder()
                .userId(user.getUserID())
                .username(user.getUsername())
                .email(user.getEmail())
                .posts(responses)
                .build();
    }


    /* ------------------- User Posts ----------------- */
    private PostResponse mapToPostResponse(Post post) {
        List<CommentResponse> commentResponses = post.getComments().stream().map(this::mapToCommentResponse).toList();
        return PostResponse.builder()
                .postId(post.getPostID())
                .postBody(post.getPostBody())
                .createdOn(post.getCreatedOn())
                .comments(commentResponses)
                .build();
    }
    @Transactional
    @Override
    public ResponseEntity<?> createUserPost(PostRequest request) {
        if (userRepository.findById(request.getUserId()).isPresent()) {
            User user = userRepository.findById(request.getUserId()).get();
            Post post = Post.builder()
                    .createdOn(LocalDateTime.now())
                    .user(user)
                    .postBody(request.getPostBody())
                    .build();

            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
    }

    @Override
    public ResponseEntity<?> findPostDetailsByPostId(Long id) {
        if(postRepository.findById(id).isPresent()) {
            Post post = postRepository.findById(id).get();
            List<CommentResponse> commentResponses = post.getComments().stream().map(this::mapToCommentResponse).toList();
            PostResponse response = PostResponse.builder()
                    .postId(post.getPostID())
                    .postBody(post.getPostBody())
                    .createdOn(post.getCreatedOn())
                    .comments(commentResponses)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist.");
    }

    @Override
    public ResponseEntity<?> getAllAvailablePosts() {
        List<Post> allPosts = postRepository.findAll();
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        TreeSet<Post> arrangedPosts = new TreeSet<>(Comparator.comparing(Post::getCreatedOn).reversed());
        arrangedPosts.addAll(allPosts);

        List<PostResponse> responses = arrangedPosts.stream().map(this::mapToPostResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @Override
    public ResponseEntity<?> editUserPost(PatchPostRequest request) {
        if(postRepository.findById(request.getPostId()).isPresent()) {
            Post post = postRepository.findById(request.getPostId()).get();
            post.setPostBody(request.getPostBody());
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body("Post edited successfully.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Post does not exist.");
    }

    @Transactional
    @Override
    public ResponseEntity<?> deletePost(DeletePostRequest request) {
        if(postRepository.findById(request.getPostId()).isPresent()) {
            postRepository.deleteById(request.getPostId());
            return ResponseEntity.status(HttpStatus.OK).body("Post deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist.");
    }


    /* ------------------- User Posts Comments ----------------- */
    @Override
    public ResponseEntity<?> createPostComment(CommentRequest request) {
        if (postRepository.findById(request.getPostId()).isPresent() &&
                userRepository.findById(request.getUserId()).isPresent()) {
            User user = userRepository.findById(request.getUserId()).get();
            Post post =postRepository.findById(request.getPostId()).get();
            Comment comment = Comment.builder()
                    .commentBody(request.getCommentBody())
                    .createdOn(LocalDateTime.now())
                    .user(user)
                    .post(post)
                    .build();
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist.");
    }



    private CommentResponse mapToCommentResponse(Comment comment) {
        CommentCreator commentCreator = CommentCreator.builder()
                .userId(comment.getUser().getUserID())
                .username(comment.getUser().getUsername()).build();
        return CommentResponse.builder()
                .commentBody(comment.getCommentBody())
                .commentId(comment.getCommentID())
                .commentCreator(commentCreator)
                .build();
    }

    @Override
    public ResponseEntity<?> getCommentByCommentId(Long id) {
        if(commentRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(mapToCommentResponse(commentRepository.findById(id).get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist.");
    }

    @Override
    public ResponseEntity<?> editUserComment(PatchCommentRequest request) {
        if(commentRepository.findById(request.getCommentId()).isPresent()) {
            Comment comment = commentRepository.findById(request.getCommentId()).get();
            comment.setCommentBody(request.getCommentBody());
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body("Comment edited successfully.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Comment does not exist.");
    }

    @Override
    public ResponseEntity<?> deleteComment(DeleteCommentRequest request) {
        if(commentRepository.findById(request.getCommentId()).isPresent()) {
            commentRepository.deleteById(request.getCommentId());
            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist.");
    }


}
