package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentRequest {
    private String commentBody;
    private Long postId;
    private Long userId;
}
