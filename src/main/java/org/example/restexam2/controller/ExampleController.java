package org.example.restexam2.controller;


import org.example.restexam2.domain.User;
import org.example.restexam2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 데이타를 넘기는 뷰로 넘기지 않는
@RequestMapping("/api")
public class ExampleController {
    @Autowired
    UserService userService;


    @GetMapping("/example")
    public ResponseEntity<String>  getExample() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Custom_Header", "dodo")
                .body("Hello!!");
    }

    // httpMessageConverter  java > json, json > java
    @GetMapping(value = "/user/{id}" , produces =  "application/json")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {


        User user  = userService.getUser(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }


    }



}
