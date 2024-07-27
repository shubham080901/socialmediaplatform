package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentCreator {
    private Long userId;
    private String username;
}
