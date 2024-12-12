/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.controller;

import com.SalesForce.model.dto.GenericResponse;
import com.SalesForce.model.dto.UserDTO;
import com.SalesForce.services.SalesforceService;
import com.SalesForce.services.UserServices;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserServices userServices;
    private final SalesforceService salesforceService;

    public UserController(UserServices userServices, SalesforceService salesforceService) {
        this.userServices = userServices;
        this.salesforceService = salesforceService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping
    public GenericResponse createUsers(@RequestBody UserDTO userDTO) {
         return new GenericResponse(userServices.createUsers(userDTO));
    }




//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        logger.info("Received request to create user: {}", userDTO);
//        try {
//            UserDTO createdUser = userServices.createUsers(userDTO);
//            logger.info("User created successfully: {}", createdUser);
//            return ResponseEntity.ok(createdUser);
//        } catch (Exception e) {
//            logger.error("Unexpected error creating user", e);
//            return ResponseEntity.internalServerError().build();
//        }
//    }





//    @PatchMapping("/{id}")
//    public GenericResponse updateUsers(@PathVariable String id,@RequestBody UserDTO userDTO) throws Exception {
//        return new GenericResponse(userServices.updateUsers(id,userDTO));
//    }


//    @PatchMapping("/{id}")
//    public GenericResponse updateUsers(@PathVariable String id, @RequestBody UserDTO userDTO) throws Exception {
//        // Ensure userDTO has name and email fields
//        return new GenericResponse(salesforceService.updateUser(id, userDTO.getFirstName(), userDTO.getEmail()));
//    }




    @PatchMapping("/update/{id}")
    public GenericResponse updateUsers(@PathVariable String id, @RequestBody UserDTO userDTO) throws Exception {
        // Validate required fields
        if (userDTO.getFirstName() == null || userDTO.getEmail() == null) {
            throw new Exception("Missing required fields: name or email");
        }

        // Call the Salesforce service to update the user
        return new GenericResponse(salesforceService.updateUser(id, userDTO.getFirstName(), userDTO.getEmail()));
    }


    @GetMapping("/{id}")
    public GenericResponse getByUserId(@PathVariable String id) throws Exception {
        return new GenericResponse(userServices.getByUserId(id));
    }


    @GetMapping("/getuser/{id}")
    public GenericResponse getUserId(@PathVariable String id) throws Exception {
        return new GenericResponse(salesforceService.getUser(id));
    }


    @GetMapping
    public GenericResponse getByAllUser() {
        return new GenericResponse(userServices.getAllUsers());
    }


    @Hidden
    @DeleteMapping("/{id}")
    public GenericResponse deleteUser(@PathVariable String id) {
        return new GenericResponse(userServices.deleteUser(id));
    }


    @GetMapping("/all-users")
    public String getAllUsers() {
        try {
            return salesforceService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while fetching users: " + e.getMessage();
        }
    }



    @DeleteMapping("/delete/{id}")
    public GenericResponse removeUser(@PathVariable String id) throws Exception {
        return new GenericResponse(salesforceService.deleteUser(id));
    }

}
