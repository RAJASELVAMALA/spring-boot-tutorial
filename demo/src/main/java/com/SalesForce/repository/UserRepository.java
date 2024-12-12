/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.repository;

import com.SalesForce.model.dao.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    // Check if the email already exists in the database
    boolean existsByEmail(String email);

}
