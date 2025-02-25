package org.example.restexam2.controller;

import lombok.RequiredArgsConstructor;
import org.example.restexam2.domain.User;
import org.example.restexam2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/user2")
@RequiredArgsConstructor
public class UserController {
    // -- get
    // -- get{id}
    // -- post
    // -- put{id}
    // -- delete{id}


    private final UserService userService;


    // curl -X GET http://localhost:8080/api/user2
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    // curl -X GET http://localhost:8080/api/user2/1
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name ="id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    // curl -X POST -H "Content-type: application/json" -d "{\"name\" : \"도서방\", \"email\" : \"dds@dmail.com\"}"  http://localhost:8080/api/user2
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // curl -X PUT -H "Content-type: application/json" -d "{\"email\" : \"wwww@kkk.com\"}"  http://localhost:8080/api/user2/16
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(name ="id")  Long id, @RequestBody User user) {

        user.setId(id);
        User updatedUser = userService.update(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);

    }

   // curl -X DELETE http://localhost:8080/api/user2/17
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name ="id")  Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }



}
