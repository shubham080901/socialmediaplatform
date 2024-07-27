package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PatchCommentRequest {
    private String commentBody;
    private Long commentId;
}
