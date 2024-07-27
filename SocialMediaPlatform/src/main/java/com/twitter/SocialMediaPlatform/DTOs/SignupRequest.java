package com.twitter.SocialMediaPlatform.DTOs;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}
