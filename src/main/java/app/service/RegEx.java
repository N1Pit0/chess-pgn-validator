package app.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static int check(String regex, String input) {
        System.out.println("Regex being compiled: " + regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
