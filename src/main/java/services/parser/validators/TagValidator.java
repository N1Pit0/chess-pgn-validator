package services.parser.validators;


import services.parser.RegEx;

import java.util.HashMap;
import java.util.Map;

public class TagValidator {
    private static final String TAG_REGEX = "^\\[[A-Z]\\w+ \"[^\"]+\"\\]$";

    public Map<Integer, String> validateTags(String tags) {
        Map<Integer, String> syntaxErrors = new HashMap<>();
        String[] lines = tags.split("\n");
        int lineCounter = 1;

        for (String line : lines) {
            if (!RegEx.match(TAG_REGEX, line)) {
                syntaxErrors.put(lineCounter, "Invalid tag: " + line);
            }
            lineCounter++;
        }

        return syntaxErrors;
    }
}