package app.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static boolean match(String regex, String input) {
        System.out.print("String being matched: " + input);
        Matcher matcher = getMatcher(regex, input);

        return matcher.matches();
    }

    public static Matcher getMatcher(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input);
    }
}
