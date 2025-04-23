package com.example.myapp.springboot.controller.importexport;

import com.example.myapp.springboot.congiguration.JwtUtil;
import com.example.myapp.springboot.model.dao.User;
import com.example.myapp.springboot.repository.UserRepository;
import com.example.myapp.springboot.service.importexport.CsvService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class CsvController {

    private final CsvService csvService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public CsvController(CsvService csvService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.csvService = csvService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // Endpoint to import users from CSV
    @PostMapping("import/users")
    public String importUsers(@RequestParam("file") MultipartFile file) {
        try {
            return csvService.importUsersFromCsv(file);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while importing users", e);
        }
    }

    @GetMapping("/export/users")
    public void downloadUserTemplate(@RequestHeader("Authorization") String authorization, HttpServletResponse response) {
        try {
            // Remove "Bearer " prefix from header
            String token = authorization.replace("Bearer ", "").trim();

            // Extract username from JWT
            String username = jwtUtil.extractUsername(token);

            // Check if user exists
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user"));
            if (user != null) {
                // Proceed with file download
                csvService.downloadUserTemplate(response);
            }
        } catch (ResponseStatusException e) {
            throw e;
        }

    }

}
