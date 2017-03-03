package com.myproject.util;

/**
 * Created by Wei Hu (J) on 2017/3/3.
 */
public class StringHelper {
    public static String firstToUpper(String str) {
        char c = str.charAt(0);
        if (c >= 97 && c <= 122)
            return (char)(c - 32) + str.substring(1);
        else return str;
    }
}
