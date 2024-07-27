package com.oops.major.socialMediaPlatform.service;

import com.oops.major.socialMediaPlatform.dto.LoginRequestDto;
import com.oops.major.socialMediaPlatform.dto.SignUpRequestDto;
import com.oops.major.socialMediaPlatform.dto.UserDetailsResponseDto;
import com.oops.major.socialMediaPlatform.dto.UserDto;
import com.oops.major.socialMediaPlatform.model.Users;

import java.util.List;

public interface UserService {

    Users save(Users users);

    Users findUserById(int userId);

    String login(LoginRequestDto loginRequestDto);

    String signup(SignUpRequestDto signUpRequestDto);

    UserDto getUserDetailsByUserId(int userId);
    List<UserDto> getAllUsers();

}
