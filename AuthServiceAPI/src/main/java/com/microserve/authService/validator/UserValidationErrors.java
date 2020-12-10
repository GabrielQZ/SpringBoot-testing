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

    public static String credentialInUseError(String fieldName, String value) {
        return "'" + value + "' is already being used for " + fieldName;
    }

    public static String invalidEmailError(String value) {
        return "'" + value + " is an invalid email.";
    }

    public static String passwordSpecialCharError() {
        return "Passwords must contain at least one special character.";
    }

    public static String passwordCaseCharError() {
        return "Passwords must contain at least one uppercase and one lowercase character.";
    }
}
