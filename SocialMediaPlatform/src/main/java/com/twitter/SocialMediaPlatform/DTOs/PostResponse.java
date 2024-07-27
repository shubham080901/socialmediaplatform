package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PostResponse {
    private Long postId;
    private String postBody;
    private LocalDateTime createdOn;
    List<CommentResponse> comments;
}
