package com.example.myapp.springboot.controller;

import com.example.myapp.springboot.model.dao.User;
import com.example.myapp.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<User> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "username") String sortField
    ) {
        return userService.searchUsers(keyword, id, page - 1, size, sortDir, sortField);
    }
}
