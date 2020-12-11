package com.microserve.authService.validator;

import org.json.JSONObject;

public class UserValidationErrors {

    public static String credentialsDoNotMatch () {
        return new JSONObject("{error: \"Login Failed: Credentials Do Not Match\"}").toString();
    }

    public static String loggingInServerError () {
        return new JSONObject("{error: \"Login Failed: Server Could Not Log You In\"}").toString();
    }

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

    public static String unhandledError(String message) {
        return new JSONObject("{error: " + message + " }").toString();
    }

    public static Object invalidRequestDataFormat() {
        return new JSONObject("{error: Request Data Was In An Invalid Format }").toString();
    }
}
