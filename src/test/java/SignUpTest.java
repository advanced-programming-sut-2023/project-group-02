import org.junit.jupiter.api.*;

import controllers.*;
import utils.*;
import view.enums.SignUpMenuMessages;

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
            Assertions.assertEquals(Validation.validatePassword(Randoms.getPassword()).size(),0);
        }
    }


    @Test
    void passwordValidation() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%Nima","%Nima","Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.WEAK_PASSWORD);
    }

    @Test
    void checkPasswordErrors() {
        ArrayList<PasswordProblem> problems = Validation.validatePassword("1Nima");
        ArrayList<PasswordProblem> expectedProblems = new ArrayList<>(List.of(PasswordProblem.TOO_SHORT,PasswordProblem.NO_SPECIAL_CHARACTER));
        Assertions.assertEquals(problems,expectedProblems);
    }

    @Test
    void usernameValidation() {
        Assertions.assertFalse(Validation.isValidUsername("Nima?"));
    }

    @Test
    void emailValidation1() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Oos.gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.INVALID_EMAIL);
    }

    @Test
    void emailValidation2() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Oos@gmailcom","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.INVALID_EMAIL);
    }

    @Test
    void emailValidation3() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%1Nima","Moazzen","Ofs..s@gmail.co.m","hello");
        Assertions.assertNull(message);
    }

    @Test
    void getRandomPassword() {
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","random",null,"Moazzen","Oos@gmail.com","hello");
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
    void emptyField1() {
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
        SignUpMenuMessages message = SignUpMenuController.initiateSignup("Nima","%1Nima","%fs1Nima","Moazzen","Oos@gmail.com","hello");
        Assertions.assertEquals(message,SignUpMenuMessages.PASSWORD_CONFIRMATION_WRONG);
    }
}
