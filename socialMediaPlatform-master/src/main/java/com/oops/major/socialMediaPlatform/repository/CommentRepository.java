package com.oops.major.socialMediaPlatform.repository;

import com.oops.major.socialMediaPlatform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select r from Comment r where r.id=?1")
    Comment findCommentById(int commentId);

    @Query("select r from Comment r where r.post.id=?1")
    List<Comment> findAllCommentsInAPost(int postId);

}
