package com.example.adminService.utils;

import com.example.adminService.security.SecurityConstants;

import java.util.UUID;

public class Utilities {

        public static String generateId(String prefix_param, String format_param, int sequence) {
            return prefix_param + String.format(format_param, sequence);
        }

        public static boolean validString(String input) {
            input = formatString(input);
            return input.length()>4;
        }
        public static boolean validBearerToken(String token) {
            return token!=null && token.startsWith(SecurityConstants.JWT_PREFIX);
        }

        public static String formatString(String input) {
            if(input != null){
                input = input.replaceAll("[^a-zA-Z0-9 ]", "");
                input = input.trim();
            }
            return input;
        }


        public static boolean validPage(int page) {
            return page>-1;
        }
        public static boolean validNumber(int input) {
            return input>0;
        }
        public static boolean validNumber(float input) { return input>0; }
        public static boolean validNumberEqualToZero(float input) { return input>=0; }

        public static String generateUuid() {
            return UUID.randomUUID().toString();
        }
    }
