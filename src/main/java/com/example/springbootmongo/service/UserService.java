package com.example.springbootmongo.service;

import com.example.springbootmongo.httpexception.UserException;
import com.example.springbootmongo.model.User;
import com.example.springbootmongo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    @Autowired
    public UsersRepository repository;


    public String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    public User addUser(User user) {
        String password = user.getPassword();
        String encodedPassword = encryptPassword(password);
        user.setPassword(encodedPassword);
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String uid) {
        return repository.findById(uid).get();
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
                throw new UserException("User or password don't match");
            } else {
                return ResponseEntity.ok(user);
            }
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public User updateUser(User user){

        User existingUser = findByUsername(user.getUsername());
        existingUser.setPassword(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(encryptPassword(user.getPassword()));
        return repository.save(existingUser);
    }

    public ResponseEntity<Object> deleteUser(String uid){

        User user = getUserById(uid);
        User de = null;
        try {
            if(user == null){
                throw new UserException("User not found");
            }
        }
        catch(UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        repository.deleteById(uid);
        return ResponseEntity.ok(user.getUsername() + " deleted successfully");

    }
}
