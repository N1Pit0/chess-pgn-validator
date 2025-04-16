import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegexTest {
    public static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        return matches;
    }

    @Test
    public void test(){
        int matches = runTest("(\\[\\w+ \"[\\w\"]+\"\\])+$", "[Event \"dsadsad\"]");

        assertTrue(matches > 0);
    }
}
