package com.example.springbootmongo.repository;

import com.example.springbootmongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User,String> {

    User findByEmail(String email);
    User findByUsername(String username);
}
