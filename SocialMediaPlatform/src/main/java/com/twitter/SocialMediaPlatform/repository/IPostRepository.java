package com.twitter.SocialMediaPlatform.repository;

import com.twitter.SocialMediaPlatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
}
