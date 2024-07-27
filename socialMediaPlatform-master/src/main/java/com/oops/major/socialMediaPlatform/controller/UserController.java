package com.oops.major.socialMediaPlatform.controller;

import com.oops.major.socialMediaPlatform.constant.AppConstant;
import com.oops.major.socialMediaPlatform.dto.*;
import com.oops.major.socialMediaPlatform.service.UserService;
import com.oops.major.socialMediaPlatform.util.AppUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        String response = userService.login(loginRequestDto);
        if (response.equals(AppConstant.LOGIN_SUCCESSFUL)) {
            return ResponseEntity.ok(response);
        }
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(response);
        return ResponseEntity.ok(errorDto);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        String response = userService.signup(signUpRequestDto);
        if (response.equals(AppConstant.ACCOUNT_ALREADY_EXISTS)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserDetailsByUserId(
            @RequestParam(value = "userID", required = true) final Integer userID) {
        UserDto userDto = userService.getUserDetailsByUserId(userID);
        if (ObjectUtils.isEmpty(userDto)) {
            String response = AppConstant.USER_DOES_NOT_EXIST;
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage(response);
            return ResponseEntity.ok(errorDto);
        }
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
