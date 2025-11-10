package model.validator;


import model.User;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;
    private final List<String> errors = new ArrayList<>();
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String username, String password) {
        errors.clear();
        validatEmailUniqueness(username);
        validateEmail(username);
        validatePasswordLenght(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    private void validatePasswordSpecial(String password) {
        if(!password.matches(".*[0-9].*")) {
            errors.add("Password must contain at least one digit");
        }
    }

    private void validatePasswordDigit(String password) {
        if(!password.matches(".*[!@#$%^&*()_+].*")){
            errors.add("Password must contain at least one special character");
        }
    }

    private void validatePasswordLenght(String password) {
        if (!(password.length() >= MIN_PASSWORD_LENGTH)) {
            errors.add("Password should be at least 8 characters characters");
        }
    }

    private void validatEmailUniqueness(String email) {
        final boolean response = userRepository.existsByUserName(email);
        if (!response) {
            errors.add("Email is already taken");
        }
    }

    private void validateEmail(String email) {
        if(!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is invalid");
        }
    }

//    private void validateUsername(String username){
//        if (!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()){
//            errors.add("Email is not valid!");
//        }
//    }
//
//    private void validatePassword(String password){
//        if (password.length() < MIN_PASSWORD_LENGTH){
//            errors.add(String.format("Password must be at least %d characters long!", MIN_PASSWORD_LENGTH));
//        }
//
//        if (!containsSpecialCharacter(password)){
//            errors.add("Password must contain at least one special character.");
//        }
//
//        if (!containsDigit(password)){
//            errors.add("Password must contain at least one digit!");
//        }
//    }
//
//    private boolean containsSpecialCharacter(String password){
//        if (password == null || password.trim().isEmpty()){
//            return false;
//        }
//        // black list
//        Pattern specialCharactersPattern = Pattern.compile("[^A-Za-z0-9]");
//        Matcher specialCharactersMatcher = specialCharactersPattern.matcher(password);
//
//        return specialCharactersMatcher.find();
//    }
//
//    private boolean containsDigit(String password) {
//        return Pattern.compile(".*[0-9].*").matcher(password).find();
//    }
//
    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}