package app.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static boolean check(String regex, String input) {
        System.out.print("String being matched: " + input);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}
