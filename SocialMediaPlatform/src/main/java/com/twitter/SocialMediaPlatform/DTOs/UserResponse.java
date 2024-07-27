package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserResponse {
    private String username;
    private Long userId;
    private String email;
    List<PostResponse> posts;
}
