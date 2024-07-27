package com.oops.major.socialMediaPlatform.repository;

import com.oops.major.socialMediaPlatform.model.Post;
import com.oops.major.socialMediaPlatform.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("select r from Users r where r.id=?1")
    Users findUserById(int userId);

    @Query("select r from Users r where r.email=?1")
    Users findUserByEmail(String email);

    @Query("select r from Users r")
    List<Users> findAllUsers();
}
