package com.rentmytech.demo.services;

import com.rentmytech.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserById(long userid);

    List<User> findByNameContaining(String username);

    User findByName(String name);

    void delete(long userid);

    User save(User user);

    User update(User user, long userid);


    void deleteAll();
}
