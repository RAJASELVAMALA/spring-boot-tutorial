/*
 * Copyright (c) ICANIO
 */

package com.SalesForce.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
//    private String companyName;

    private String salesforceId;

}
