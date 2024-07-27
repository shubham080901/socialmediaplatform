package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PatchPostRequest {
    private String postBody;
    private Long postId;
}
