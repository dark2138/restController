package org.example.restexam2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ErrorTestController {

    @GetMapping("/api/eTest")
    public String eTest() {
        throw new RuntimeException("API에서 에러 발생");
    }



    @GetMapping("/api/n")
    public String test2(@RequestParam(name = "id") Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID가 없읍");
        }
        throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID provided");
    }



}
