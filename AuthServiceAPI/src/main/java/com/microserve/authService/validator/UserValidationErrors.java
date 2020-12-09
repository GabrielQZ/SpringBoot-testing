package com.microserve.authService.validator;

public class UserValidationErrors {

    protected static String requiredError(String fieldName) {
        return fieldName + " is required to create an account.";
    }

    protected static String invalidTypeError(String fieldName ) {
        return "Invalid data given for '" + fieldName + "'.";
    }

    public static String invalidLengthError(String fieldName, int min, int max) {
        return "Invalid length for '" + fieldName + "', must be between " + min + " and " + max + " characters.";
    }
}
