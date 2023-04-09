package utils;

import java.util.Random;

public class Randoms
{
    static String[] slogans = {
            "I shall have my revenge, in this life or the next",
            "I can do whatever I want",
            "I am the greatest player of the game",
            "The only impossible thing is the word \"impossible\"!",
            "This is where the magic happens",
            "I can, so I do",
            "A liar is enemy of God"
    };

    public static String getPassword(){
        String specialCharacters = "!@#$%^&*()_+";
        Random random = new Random();
        int length = random.nextInt(14) + 6;
        char[] password = new char[length];

        password[0] = (char) (random.nextInt(26) + 'A');
        password[1] = (char) (random.nextInt(26) + 'a');
        password[2] = (char) (random.nextInt(10) + '0');
        password[3] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));

        for (int i = 4; i < length; i++) {
            password[i] = (char) (random.nextInt(90) + 33);
        }

        return new String(password);
    }

    public static String getSlogan(){
        Random random = new Random();
        return slogans[random.nextInt(slogans.length)];
    }
}
