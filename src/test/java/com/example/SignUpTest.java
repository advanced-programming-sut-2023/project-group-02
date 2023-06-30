package com.example;

import org.junit.jupiter.api.*;

import client.view.enums.SignUpMenuMessages;
import controllers.*;
import utils.*;

import java.util.ArrayList;
import java.util.List;

public class SignUpTest {
    @BeforeEach
    void beforeEach() {
        SignUpMenuController.reset();
    }

    @Test
    void testRandomPasswordValidation() {
        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals(Validation.validatePassword(Randoms.getPassword()).size(), 0);
        }
    }

    @Test
    void passwordValidation() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima", "%Nima", "%Nima", "Moazzen",
                "Oos@gmail.com", "hello");
        Assertions.assertEquals(message, SignUpMenuMessages.WEAK_PASSWORD);
    }

    @Test
    void checkPasswordErrors() {
        ArrayList<PasswordProblem> problems = Validation.validatePassword("1Nima");
        Assertions.assertTrue(problems.contains(PasswordProblem.TOO_SHORT));
        Assertions.assertTrue(problems.contains(PasswordProblem.NO_SPECIAL_CHARACTER));
        Assertions.assertFalse(problems.contains(PasswordProblem.NO_LOWERCASE));
        Assertions.assertFalse(problems.contains(PasswordProblem.NO_UPPERCASE));
        Assertions.assertFalse(problems.contains(PasswordProblem.NO_DIGIT));
    }

    @Test
    void usernameValidation() {
        Assertions.assertFalse(Validation.isValidUsername("Nima?"));
        Assertions.assertTrue(Validation.isValidUsername("Nima"));
        Assertions.assertTrue(Validation.isValidUsername("NimaNM7"));
        Assertions.assertTrue(Validation.isValidUsername("Nima____NM7"));
        Assertions.assertFalse(Validation.isValidUsername("Nima-NM7"));
        Assertions.assertFalse(Validation.isValidUsername("Nima.NM7"));
        Assertions.assertEquals(
                SignUpMenuController.initiateSignup("Nima?", "Pass123!", "Pass123!", "NM7", "n@i.ma", "random"),
                SignUpMenuMessages.INVALID_USERNAME);
    }

    @Test
    void emailValidation() {
        Assertions.assertFalse(Validation.isValidEmail("Oos.gmail.com"));
        Assertions.assertFalse(Validation.isValidEmail("Oos@gmailcom"));
        Assertions.assertFalse(Validation.isValidEmail("Ofs..s@gmail.co.m"));
        Assertions.assertTrue(Validation.isValidEmail("Ofs.s@gmail.c.o.m"));
        Assertions.assertTrue(Validation.isValidEmail("Ofss@gmail.com"));
        Assertions.assertEquals(
                SignUpMenuController.initiateSignup("Nima", "%1Nima", "%1Nima", "Moazzen", "Oos.gmail.com", "hello"),
                SignUpMenuMessages.INVALID_EMAIL);
    }

    @Test
    void getRandomPassword() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima", "random", null, "Moazzen",
                "Oos@gmail.com", "hello");
        Assertions.assertNotNull(SignUpMenuController.getPassword());
        Assertions.assertEquals(message, SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED);
    }

    @Test
    void getRandomSlogan() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima", "Pass123!", "Pass123!", "Moazzen",
                "Oos@gmail.com", "random");
        Assertions.assertNotNull(SignUpMenuController.getSlogan());
        Assertions.assertNull(message);
    }

    @Test
    void emptyFields() {
        Assertions.assertEquals(
                SignUpMenuController.initiateSignup("Nima", "%1Nima", "%1Nima", "", "Oos@gmail.com", "hello"),
                SignUpMenuMessages.EMPTY_NICKNAME);
        Assertions.assertEquals(SignUpMenuController.initiateSignup("Nima", "%1Nima", "%1Nima", "Moazzen", "", "hello"),
                SignUpMenuMessages.EMPTY_EMAIL);
        Assertions.assertEquals(
                SignUpMenuController.initiateSignup("", "%1Nima", "%1Nima", "Moazzen", "i@d.c", "hello"),
                SignUpMenuMessages.EMPTY_USERNAME);
    }

    @Test
    void confirmationWrong() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima", "%1Nima", "%fs1Nima", "Moazzen",
                "Oos@gmail.com", "hello");
        Assertions.assertEquals(message, SignUpMenuMessages.PASSWORD_CONFIRMATION_WRONG);
    }
}
