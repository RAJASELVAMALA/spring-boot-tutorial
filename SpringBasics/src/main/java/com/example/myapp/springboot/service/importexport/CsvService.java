package com.example.myapp.springboot.service.importexport;

import com.example.myapp.springboot.model.dao.User;
import com.example.myapp.springboot.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CsvService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String importUsersFromCsv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uploaded file is empty.");
        }

        List<User> users = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            boolean isFirstRow = true;

            while ((line = csvReader.readNext()) != null) {
                if (isFirstRow && isHeaderRow(line)) {
                    isFirstRow = false;
                    continue;
                }
                isFirstRow = false;

                validateRowLength(line);

                String username = line[0].trim();
                String password = line[1].trim();

                validateUsername(username);
                validatePassword(password);
                checkUserAlreadyExists(username);

                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                users.add(user);
            }

            if (users.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid users found in the file.");
            }

            userRepository.saveAll(users);
            return "Users imported successfully!";

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to import users: " + e.getMessage());
        }
    }

    // --- 🔒 PRIVATE VALIDATION METHODS BELOW ---

    private boolean isHeaderRow(String[] row) {
        return row.length >= 2 &&
                "username".equalsIgnoreCase(row[0]) &&
                "password".equalsIgnoreCase(row[1]);
    }

    private void validateRowLength(String[] row) {
        if (row.length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each row must contain at least username and password.");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty.");
        }

        if (!username.matches("^[a-zA-Z0-9._-]{3,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username format: " + username);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be empty.");
        }

        if (password.length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long.");
        }
    }

    private void checkUserAlreadyExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists: " + username);
        }
    }

    public void downloadUserTemplateuser(HttpServletResponse response) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Username");
            headerRow.createCell(1).setCellValue("Password");

            Row sampleRow = sheet.createRow(1);
            sampleRow.createCell(0).setCellValue("john_doe");
            sampleRow.createCell(1).setCellValue("password123");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=UserTemplate.xlsx");

            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate Excel file");
        }
    }



        public void downloadUserTemplate(HttpServletResponse response) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Users");

                // Header
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Username");
                headerRow.createCell(1).setCellValue("Password");

                // Fetching data from the database
                List<User> users = userRepository.findAll();

                int rowIndex = 1;
                for (User user : users) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(user.getUsername());
                    row.createCell(1).setCellValue(user.getPassword());
                }

                // Set content type and headers
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=UserList.xlsx");

                workbook.write(response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate Excel file");
            }
        }


}
