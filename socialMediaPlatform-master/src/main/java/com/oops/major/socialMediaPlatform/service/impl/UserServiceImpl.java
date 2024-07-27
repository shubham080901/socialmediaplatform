package com.oops.major.socialMediaPlatform.service.impl;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.model.Comment;
import com.oops.major.socialMediaPlatform.model.Post;
import com.oops.major.socialMediaPlatform.model.Users;
import com.oops.major.socialMediaPlatform.repository.CommentRepository;
import com.oops.major.socialMediaPlatform.repository.PostRepository;
import com.oops.major.socialMediaPlatform.repository.UserRepository;
import com.oops.major.socialMediaPlatform.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Users users = userRepository.findUserByEmail(email);
        if (ObjectUtils.isEmpty(users) || ObjectUtils.isEmpty(users.getEmail())) {
            return AppConstant.USER_DOES_NOT_EXIST;
        }
        if (ObjectUtils.isNotEmpty(users.getPassword())) {
            if (!password.equals(users.getPassword())) {
                return AppConstant.USERNAME_OR_PASSWORD_INCORRECT;
            }
        }
        return AppConstant.LOGIN_SUCCESSFUL;
    }

    @Override
    public String signup(SignUpRequestDto signUpRequestDto) {
        Users users = userRepository.findUserByEmail(signUpRequestDto.getEmail());
        if (ObjectUtils.isNotEmpty(users)) {
            return AppConstant.ACCOUNT_ALREADY_EXISTS;
        }
        users = new Users();
        users.setEmail(signUpRequestDto.getEmail());
        users.setName(signUpRequestDto.getName());
        users.setPassword(signUpRequestDto.getPassword());
        users = userRepository.save(users);
        return AppConstant.ACCOUNT_CREATION_SUCCESSFUL;
    }

    @Override
    public UserDto getUserDetailsByUserId(int userId) {
        UserDto userDto = null;
        Users user = userRepository.findUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUserID(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> usersList = userRepository.findAllUsers();
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto = null;
        for (Users user : usersList) {
            userDto = new UserDto();
            userDto.setUserID(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private static List<CommentDto> getCommentDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentCreatorDto commentCreatorDto = new CommentCreatorDto();
            commentCreatorDto.setUserID(comment.getUser().getId());
            commentCreatorDto.setName(comment.getUser().getName());

            CommentDto commentDto = new CommentDto();
            commentDto.setCommentBody(comment.getCommentBody());
            commentDto.setCommentID(comment.getId());
            commentDto.setCommentCreator(commentCreatorDto);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
