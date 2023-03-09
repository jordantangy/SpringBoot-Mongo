package com.example.springbootmongo.service;

import com.example.springbootmongo.httpexception.UserException;
import com.example.springbootmongo.model.User;
import com.example.springbootmongo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UsersRepository repository;


    public String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    public User addUser(User user) {
        boolean isUnique = repository.findAll().stream()
                                               .noneMatch(existingUser -> existingUser.getUsername().equals(user.getUsername()));
        if(isUnique){
            String password = user.getPassword();
            String encodedPassword = encryptPassword(password);
            user.setPassword(encodedPassword);
        }
        else{
            throw new UserException("Username already exists, choose another username");
        }
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(String uid) {
        Optional<User> user = repository.findById(uid);
        return user;
    }

    private User checkUserExists(String uid) {
        Optional<User> optionalUser = getUserById(uid);
        if (optionalUser.isEmpty()) {
            throw new UserException("User not found");
        }
        return optionalUser.get();
    }


    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }


    public ResponseEntity<Object> checkCredentials(User user, String password, String hashedPassword) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(password, hashedPassword);
            if (!passwordMatches) {
                throw new UserException("username or password is not correct");
            } else {
                return ResponseEntity.ok(user);
            }
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public User updateUser(String uid,User user){

        User existingUser = checkUserExists(uid);
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setPassword(encryptPassword(user.getPassword()));
        return repository.save(existingUser);
    }

    public ResponseEntity<Object> deleteUser(String uid){

        User user = checkUserExists(uid);
        repository.deleteById(uid);
        return ResponseEntity.ok(user.getUsername() + " deleted successfully");

    }


    public ResponseEntity<Object> updatePassword(String uid, String newPassword) {
        User user = checkUserExists(uid);
        user.setPassword(encryptPassword(newPassword));
        repository.save(user);
        return ResponseEntity.ok(user.getUsername() + " successfully modified password");
    }

}
