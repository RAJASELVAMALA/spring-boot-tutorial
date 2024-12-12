/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.services;

import com.SalesForce.model.dao.User;
import com.SalesForce.model.dto.UserDTO;
import com.SalesForce.exception.CustomException;
import com.SalesForce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServices {

    private static final Logger LOG = LoggerFactory.getLogger(UserServices.class);

    private final UserRepository userRepository;
    private final SalesforceService salesforceService;

    public UserServices(UserRepository userRepository, SalesforceService salesforceService) {
        this.userRepository = userRepository;
        this.salesforceService = salesforceService;
}

//public UserServices(UserRepository userRepository) {
//    this.userRepository = userRepository;
//    }



//
//    public UserDTO createUsers(UserDTO userDTO) throws Exception {
//        System.out.println("verunaResponse");
//
//        // Validate the user input before processing
//        validateUser(userDTO);
//
//        // Create a new User object
//        User saveUser = new User();
//        saveUser.setFirstName(userDTO.getFirstName());
//        saveUser.setLastName(userDTO.getLastName());
//        saveUser.setEmail(userDTO.getEmail());
//
//        // Save the user in the repository and convert to DTO before returning
//        User user = userRepository.save(saveUser);
//
//        // Create user in Salesforce
//        System.out.println(user.getEmail() + " " + user.getFirstName() + " " + user.getLastName());
//        salesforceService.createUser(user.getFirstName(), user.getEmail());
//
//        return convertToDTO(user);
//    }
//




//    @Transactional
//    public UserDTO createUsers(UserDTO userDTO) {
//        LOG.info("verunaResponse");
//
//        // Validate the user input before processing
//        if (userDTO == null) {
//            throw new IllegalArgumentException("UserDTO cannot be null");
//        }
//
//        validateUser(userDTO);
//
//        // Create a new User object
//        User saveUser = new User();
//        saveUser.setFirstName(userDTO.getFirstName());
//        saveUser.setLastName(userDTO.getLastName());
//        saveUser.setEmail(userDTO.getEmail());
//
//        // Save the user in the repository
//        User user = userRepository.save(saveUser);
//
//        // Create user in Salesforce
//        try {
//            LOG.info("Creating user in Salesforce: " + user.getEmail());
//            salesforceService.createUser(user.getFirstName(), user.getEmail());
//        } catch (Exception e) {
//            LOG.error("Salesforce user creation failed for user: " + user.getEmail(), e);
//        }
//
//        // Convert to DTO and return
//        return convertToDTO(user);
//    }




    @Transactional
    public UserDTO createUsers(UserDTO userDTO) {
        LOG.info("verunaResponse");

        // Validate the user input before processing
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        validateUser(userDTO);

        // Create a new User object
        User saveUser = new User();
        saveUser.setFirstName(userDTO.getFirstName());
        saveUser.setLastName(userDTO.getLastName());
        saveUser.setEmail(userDTO.getEmail());

        // Initialize variable to store Salesforce ID
        String salesforceId = null;

        // Create user in Salesforce
        try {
            LOG.info("Creating user in Salesforce: " + saveUser.getEmail());
            // Assuming the createUser method returns the Salesforce ID
            salesforceId = salesforceService.createUser(saveUser.getFirstName(), saveUser.getEmail());
            LOG.info("Salesforce user created with ID: " + salesforceId);
        } catch (Exception e) {
            LOG.error("Salesforce user creation failed for user: " + saveUser.getEmail(), e);
            // You can decide whether to fail the transaction or continue based on your business logic
            throw new RuntimeException("Salesforce user creation failed");
        }

        // Set the Salesforce ID to the User object if the Salesforce creation was successful
        if (salesforceId != null) {
            saveUser.setSalesforceId(salesforceId);
        }

        // Save the user in the local repository
        User user = userRepository.save(saveUser);

        // Convert to DTO and return
        return convertToDTO(user);
    }






//    public User createUser(User user) throws Exception {
//        System.out.println("Entered"+ user.getEmail() + " " + user.getFirstName() + " " + user.getLastName());
//
//        // Save user to local database
//        User savedUser = userRepository.save(user);
//
//        // Create user in Salesforce
//        System.out.println(user.getEmail() + " " + user.getFirstName() + " " + user.getLastName());
//        salesforceService.createUser(user.getFirstName(), user.getEmail());
//        return savedUser;
//    }



    // Validate the user's email, checking for emptiness, existence, and format
    private void validateUser(UserDTO userDTO) {
        // Basic email validation before setting the email
        String email = userDTO.getEmail();
        if (email == null || email.trim().isEmpty()) {
            throw new CustomException("Email cannot be empty", HttpStatus.BAD_REQUEST);
        }

        // Check if the email already exists in the database
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("Email Already Exists", HttpStatus.BAD_REQUEST);
        }

        // Additional validation for email format
        if (!isValidEmail(email)) {
            throw new CustomException("Invalid Email Format", HttpStatus.BAD_REQUEST);
        }
    }



    // Check if an email has a valid format using a basic regular expression
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }


//    @Transactional
//    public UserDTO updateUsers(String id, UserDTO userDTO) throws Exception {
//        // Find the existing user by ID
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
//
//        // Validate and update user fields
//        validateAndUpdateUser(existingUser, userDTO);
//
//        // Save the updated user to the repository
//        User updatedUser = userRepository.save(existingUser);
//
//        System.out.println("existingUser: " + existingUser.getSalesforceId());
//        System.out.println("updatedUser: " + updatedUser.getSalesforceId());
//        // Call Salesforce service to update the user
//        try {
//            String salesforceId = salesforceService.updateUser(
//                    updatedUser.getSalesforceId(),
//                    updatedUser.getLastName(),
//                    updatedUser.getEmail()
//            );
//            // Update the Salesforce ID if it has changed
//            if (!salesforceId.equals(existingUser.getSalesforceId())) {
//                existingUser.setSalesforceId(salesforceId);
//                updatedUser = userRepository.save(existingUser);
//            }
//        } catch (Exception e) {
//            // Log the error and throw a custom exception
//            // You might want to implement a retry mechanism or queue the update for later
//            throw new CustomException("Failed to update user in Salesforce: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        // Convert the updated user to DTO before returning
//        return convertToDTO(updatedUser);
//    }




    // Validate and update user fields based on the provided UserDTO
    private void validateAndUpdateUser(User existingUser, UserDTO userDTO) {
        // Update user fields from the DTO
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
//        existingUser.setCompanyName(userDTO.getCompanyName());
        existingUser.setEmail(userDTO.getEmail());
    }



    public UserDTO getByUserId(String id) {
        // Find the user by ID and convert to DTO, or throw an exception if not found
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }



    // Convert User entity to UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
//        userDTO.setCompanyName(user.getCompanyName());
        userDTO.setSalesforceId(user.getSalesforceId());
        return userDTO;
    }



    public List<UserDTO> getAllUsers() {
        // Fetch users from the repository
        List<User> users = userRepository.findAll();

        // Check if the list is empty
        if (users.isEmpty()) {
            throw new CustomException("No users found", HttpStatus.BAD_REQUEST);
        }

        // Convert the list of users to a list of UserDTO
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public User deleteUser(String id) {
        // Verify if the user exists, and throw an exception if not found
        getByUserId(id);

        // Delete the user by ID
        userRepository.deleteById(id);

        // Throw an exception indicating successful deletion
        throw new CustomException("User deleted successfully", HttpStatus.OK);
    }

}
