package com.example.springbootmongo.controller;

import com.example.springbootmongo.httpexception.UserException;
import com.example.springbootmongo.model.User;
import com.example.springbootmongo.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User uploadUser(@Valid @RequestBody User user){

        return service.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{uid}")
    public Optional<User> getUserById(@PathVariable String uid){
        return service.getUserById(uid);
    }

    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable String email){
        return  service.findByEmail(email);
    }



    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ObjectNode JSONObject){
        String username = JSONObject.get("username").asText().toString();
        String password = JSONObject.get("password").asText().toString();
        User user = service.findByUsername(username);
        try {
            if (user == null) {
                throw new UserException("User not found");
            }
        }catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return service.checkCredentials(user,password,user.getPassword());
    }

    @PutMapping("/update/{uid}")
    public User updateUser(@PathVariable String uid, @RequestBody User user){
        return service.updateUser(uid,user);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Object> deleteUser(@PathVariable String uid){
        return service.deleteUser(uid);
    }

    @PutMapping("updatepassword/{uid}")
    public ResponseEntity<Object>  changePassword(@PathVariable String uid, @RequestBody ObjectNode JSONObject){
        String newPassword = JSONObject.get("password").asText().toString();
        return service.updatePassword(uid,newPassword);
    }


}
