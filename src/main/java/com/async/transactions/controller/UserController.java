package com.async.transactions.controller;

import com.async.transactions.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/1")
    public ResponseEntity transactionOne() {
        userService.createAppointmentForUser(1);
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/2")
    public ResponseEntity transactionTwo() {
        userService.triggerAsyncTransactions();
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/setup")
    public ResponseEntity setupDB() {
        userService.setUpDB();
        return ResponseEntity.ok("");
    }

}
