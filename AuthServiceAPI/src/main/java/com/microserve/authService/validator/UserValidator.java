package com.microserve.authService.validator;

import com.microserve.authService.model.User;
import com.microserve.authService.service.UserService;
import org.json.JSONObject;
import org.apache.commons.validator.routines.EmailValidator;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

public class UserValidator {

    //this set is immutable and is used to ignore validation for fields defined by server
    static private final Set<String> ignoredField = Set.of("id");

    //FIELD CONSTRAINTS (avoiding magic numbers within validators)
    static private final int USERNAME_MAX = 20;
    static private final int USERNAME_MIN = 3;
    static private final int PASSWORD_MAX = 256;
    static private final int PASSWORD_MIN = 8;
    static private final int EMAIL_MAX = 256;
    static private final int EMAIL_MIN = 5;

    //PASSWORD REGEX
    static private final Pattern specialChar = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    static private final Pattern hasUpperCase = Pattern.compile("[A-Z]");
    static private final Pattern hasLowerCase = Pattern.compile("[a-z]");

    private static final HashMap<String, Integer> valueConstraints = new HashMap<>() {
        {
            put("nameMax", USERNAME_MAX);
            put("nameMin", USERNAME_MIN);
            put("passwordMax", PASSWORD_MAX);
            put("passwordMin", PASSWORD_MIN);
            put("emailMax", EMAIL_MAX);
            put("emailMin", EMAIL_MIN);
        }
    };

    static public JSONObject validateNewUser(User newUser, UserService service) {
        //get all the fields in User to loop through and preform checks (validation)
        Field[] userFields = User.class.getDeclaredFields();
        //error object that will be returned to client if not empty
        JSONObject errors = new JSONObject();
        //loop through all fields of User
        for ( Field field : userFields ) {
            //get field name to be used in creating the error report
            String fieldName = field.getName();
            //skip server defined fields for validation, fields get filtered from request data via JPA
            if (ignoredField.contains(fieldName)) continue;
            //create a list for potential errors
            List<String> fieldErrors = new ArrayList<>();

            try {
                //general validation check (make sure data is present)
                Object fieldValue = field.get(newUser);
                if (fieldValue == null ) {
                    fieldErrors.add(
                            UserValidationErrors.requiredError(fieldName));
                    continue;
                } else if (!(fieldValue instanceof String)) {
                    fieldErrors.add(
                            UserValidationErrors.requiredError(fieldName));
                    continue;
                }

                String value = (String) fieldValue;

                //check min/max constraints
                int minLen = valueConstraints.get(fieldName+"Min");
                int maxLen = valueConstraints.get(fieldName+"Max");
                if (value.length() > maxLen || value.length() < minLen )
                    fieldErrors.add(
                            UserValidationErrors.invalidLengthError(fieldName, minLen, maxLen));

                //specific checks for fields
                switch (fieldName) {
                    case "name":
                        if (service.nameInUse(value))
                            fieldErrors.add(
                                    UserValidationErrors.credentialInUseError(fieldName, value));
                      break;
                    case "email":
                        if (service.emailInUse(value))
                            fieldErrors.add(
                                    UserValidationErrors.credentialInUseError(fieldName, value));
                        else if (!EmailValidator.getInstance().isValid(value))
                            fieldErrors.add(
                                    UserValidationErrors.invalidEmailError(value));
                        break;
                    case "password":
                        if (!specialChar.matcher(value).find())
                            fieldErrors.add(UserValidationErrors.passwordSpecialCharError());
                        if (!hasLowerCase.matcher(value).find() || !hasUpperCase.matcher(value).find() )
                            fieldErrors.add(UserValidationErrors.passwordCaseCharError());
                        break;
                    default:
                        System.out.println("Something slipped through user validation, fieldName: " + fieldName );
                }
            }
            catch (IllegalAccessException e) {
                //e.printStackTrace();
                fieldErrors.add("Server could not validate " + fieldName);
                System.out.println("IllegalAccessException (UserValidator: validateNewUser)\nCheck access modifiers");
            } finally {
                if (fieldErrors.size() != 0) errors.put(fieldName, fieldErrors);
            }
        }
        return  errors;
    }
}
