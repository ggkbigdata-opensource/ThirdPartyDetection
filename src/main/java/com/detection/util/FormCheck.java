package com.detection.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormCheck {
    
    public static boolean isEmailValid(String email) {
        boolean result = false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(email);
        if(matcher.find())
            result = true;
        return result;
    }
    
}
