package com.example.springbootmongo.repository;

import com.example.springbootmongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User,String> {

    User findByEmail(String email);
    User findByUsername(String username);
}
