package com.abacus.franchise.viewModels;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private UUID userId;

    private String email;

    private String mobile;

    private String passwordHash;

    private String firstName;

    private String middleName;
    
    private LocalDate dateOfBirth;
    
    private String lastName;

    private UUID roleId;

    private UUID franchiseId;
    
    private String franchiseName;
    
    //address
    
    private String line1;

    private String landmark;

    private String city;

    private UUID districtId;

    private UUID stateId;

    private String pincode;

    private UUID createdBy;

    private UUID updatedBy;

}
