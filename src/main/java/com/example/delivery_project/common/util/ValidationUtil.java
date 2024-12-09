package com.example.delivery_project.common.util;

public class ValidationUtil {

    public static boolean isValidPasswordFormat(String password) {

        // 최소 8자, 대소문자 포함 영문 + 숫자 + 특수문자 포함
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }
}