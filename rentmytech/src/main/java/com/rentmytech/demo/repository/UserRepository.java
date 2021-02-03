package com.rentmytech.demo.repository;

import com.rentmytech.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String name);
}
