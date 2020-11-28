package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    private final static int VALIDATION_CODE_LENGTH = 10;
    private static final int VALIDATION_CODE_CHAR_LIMIT = 1_000_000_000;

    @Id
    //@GeneratedValue //potentially could use this over uuid
    private final UUID id = UUID.randomUUID();

    private String name;

    private String email;

    private boolean isEmailValidated;
    private String emailValidationCode;

    private String password;


    public User (String name, String email) {

        emailValidationCode = genValidationCode();

        System.out.println(emailValidationCode);

        //emails can not be validated on User creation this will need to be done using a authorized put request later on
        isEmailValidated = false;

        //get data from user's request to server
        this.name = name;
        this.email = email;

        //This will be set by the request and encrypted before being passed to the data base
        //TODO allow user input, and encrypt password
        //here im thinking I can create a random password that can be reset only after the email has been validated,
        //but this should work so that the password is set on creation
        password = "UNSETPASSWORD" + emailValidationCode;//genValidationCode();
    }

    private String genValidationCode() {
        return formatValidationCode(Integer.toString((int) Math.round(Math.random() * VALIDATION_CODE_CHAR_LIMIT)));
    }

    private boolean validateEmail( String validationCode) {
        //simply check for a valid email validation code, if it is wrong the User will send false to the method caller
        if (emailValidationCode.equals(validationCode)) {
            isEmailValidated = true;
            return true;
        } else return false;
    }

    private void renameUser( String name ) {
        //TODO validate name
        this.name = name;
    }

    private String updateEmail( String email ) {
        //TODO validate email
        isEmailValidated = false;
        this.email = email;
        emailValidationCode = genValidationCode();
        return emailValidationCode;
    }


    private static String formatValidationCode ( String code ) {
        int missingZeros = VALIDATION_CODE_LENGTH - code.length();
        return "0".repeat(missingZeros) + code;
    }

}
