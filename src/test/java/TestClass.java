import org.junit.jupiter.api.*;

import controllers.*;
import models.*;
import view.*;
import utils.*;
import view.enums.SignUpMenuMessages;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
    @Test
    public void testRandomPasswordValidation() {
        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals(Validation.validatePassword(Randoms.getPassword()).size(),0);
        }
    }


    @Test
    public void passwordValidation() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%Nima","%Nima","Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.WEAK_PASSWORD);
    }

    @Test
    public void checkPasswordErrors() {
        ArrayList<PasswordProblem> problems = Validation.validatePassword("1Nima");
        ArrayList<PasswordProblem> expectedProblems = new ArrayList<>(List.of(PasswordProblem.TOO_SHORT,PasswordProblem.NO_SPECIAL_CHARACTER));
        Assertions.assertEquals(problems,expectedProblems);
    }
    @Test
    public void usernameValidation() {
        Assertions.assertFalse(Validation.isValidUsername("Nima?"));
    }

    @Test
    public void emailValidation1() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Oos.gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.INVALID_EMAIL);
    }

    @Test
    public void emailValidation2() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Oos@gmailcom","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.INVALID_EMAIL);
    }

    @Test
    public void emailValidation3() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Ofs..s@gmail.co.m","hello");
        Assertions.assertEquals(message,null);
    }

    @Test
    public void getRandomPassword() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","random",null,"Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.PASSWORD_CONFIRMATION_NEEDED);
    }

    @Test
    public void emptyField1() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.EMPTY_NICKNAME);
    }

    @Test
    public void emptyField2() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("","%1Nima","%1Nima","Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.EMPTY_USERNAME);
    }

    @Test
    public void emptyField3() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.EMPTY_EMAIL);
    }

    @Test
    public void confirmationWrong() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%fs1Nima","Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.PASSWORD_CONFIRMATION_WRONG);
    }
}
