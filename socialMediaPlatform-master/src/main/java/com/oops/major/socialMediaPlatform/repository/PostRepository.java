package com.oops.major.socialMediaPlatform.repository;

import com.oops.major.socialMediaPlatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select r from Post r where r.id=?1")
    Post findPostById(int postId);

    @Query("select r from Post r where r.user.id=?1")
    List<Post> findAllPostsByUser(int userId);
    // TODO should this also be in decreasing order??

    @Query("select r from Post r order by r.date desc")
    List<Post> findAllPosts();

}
