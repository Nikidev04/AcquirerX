package com.acquirerx.backend.switchmodule.util;

import java.util.Random;

public class AuthCodeGenerator {

    public static String generateAuthCode() {
        return "A" + (10000 + new Random().nextInt(90000));
    }
}