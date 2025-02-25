package org.example.restexam2.controller;

import org.example.restexam2.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyRestController {


    @GetMapping("/api/greating")
    public Map<String, String> great(@RequestParam(name= "msg", required=false, defaultValue = "hello") String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        map.put("fuck", msg);


        return map;
    }





    @GetMapping(value = "/api/user", produces = "application/xml")
    public User getUser(@RequestParam(name="name")String name) {
        return new User(name, "000", "경기도");
    }



    @GetMapping("/api/users")
    public List<User> getUsers() {

        return new ArrayList<User>();
    }




}
