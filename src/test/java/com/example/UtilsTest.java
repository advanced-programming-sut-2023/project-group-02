package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import utils.Utils;

public class UtilsTest {
    @Test
    public void toCamelCase() {
        Assertions.assertEquals(Utils.toCamelCase("hello  my friend"), "helloMyFriend");
        Assertions.assertEquals(Utils.toCamelCase("HELLO mY FrIENd"), "helloMyFriend");
    }
}
