package com.example.myapp.springboot.service;

import com.example.myapp.springboot.model.dao.User;
import com.example.myapp.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> searchUsers(String keyword, String id, int page, int size, String sortDir, String sortField) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (id != null && !id.isEmpty()) {
            return userRepository.findById(id, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            return userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }
}
