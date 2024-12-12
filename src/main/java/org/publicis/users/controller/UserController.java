package org.publicis.users.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.publicis.users.model.UserEntity;
import org.publicis.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/load")
    public String loadUsers() throws JsonProcessingException {
        userService.loadUsersFromExternalAPI();
        return "Users loaded successfully!";
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/role/{role}")
    public List<UserEntity> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/age/asc")
    public List<UserEntity> getUsersSortedByAgeAsc() {
        return userService.getUsersSortedByAgeAsc();
    }

    @GetMapping("/age/desc")
    public List<UserEntity> getUsersSortedByAgeDesc() {
        return userService.getUsersSortedByAgeDesc();
    }

    @GetMapping("/find")
    public UserEntity getUserByIdOrSsn(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String ssn) {
        if (id == null && ssn == null) {
            throw new IllegalArgumentException("Either ID or SSN must be provided");
        }
        return userService.getUserByIdOrSsn(id, ssn);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
