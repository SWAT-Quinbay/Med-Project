package com.example.salesService.utils;


import com.example.salesService.security.SecurityConstants;

public class Utilities {


        public static boolean validString(String input) {
            input = formatString(input);
            return input.length() > 3;
        }

        public static String formatString(String input) {
            if(input != null){
                input = input.replaceAll("[^a-zA-Z0-9 ]", "");
                input = input.trim();
            }
            return input;
        }
        public static boolean validBearerToken(String token) {
            return token!=null && token.startsWith(SecurityConstants.JWT_PREFIX);
        }


        public static boolean validNumber(int input) {
            return input>0;
        }
        public static boolean validNumber(float input) { return input>0; }

}
