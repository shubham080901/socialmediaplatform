package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentResponse {
    private Long commentId;
    private String commentBody;
    private CommentCreator commentCreator;
}
