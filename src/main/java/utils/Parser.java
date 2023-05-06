package utils;

import java.util.ArrayList;

public class Parser {
    public static ArrayList<String> tokenize(String input) {
        ArrayList<String> tokens = new ArrayList<>();
        String[] sections = input.split("\"");
        for (int i = 0; i < sections.length; i++) {
            String section = sections[i];
            if (i % 2 == 0) {
                for (String section2 : section.split("\\s+")) {
                    if (section2.length() > 0) {
                        tokens.add(section2);
                    }
                }
            } else {
                tokens.add('"' + section + '"');
            }
        }
        return tokens;
    }

    public final String input;
    private final ArrayList<String> tokens;

    public Parser(String input) {
        this.input = input;
        this.tokens = tokenize(input);
    }

    public String getInput() {
        return input;
    }

    private String removeQuotations(String str) {
        if (str.charAt(0) == '"') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    public ArrayList<String> getAll(String name) {
        boolean isShort = name.length() == 1;
        String nameWithDash = (isShort ? "-" : "--") + name;
        ArrayList<String> output = new ArrayList<>();
        boolean found = false;
        for (String token : tokens) {
            if (token.equals(nameWithDash)) {
                if (!found) {
                    found = true;
                } else {
                    // duplication error
                }
            } else if (found) {
                if (token.charAt(0) == '-') {
                    break;
                } else {
                    output.add(removeQuotations(token));
                }
            }
        }
        return found ? output : null;
    }

    public String get(String name) {
        ArrayList<String> all = getAll(name);
        if (all == null || all.size() == 0)
            return null;
        return all.get(0);
    }

    public boolean getFlag(String name) {
        return getAll(name) != null;
    }

    public String getByIndex(int index) {
        if (index > tokens.size())
            return null;
        return removeQuotations(tokens.get(index));
    }

    public boolean beginsWith(String beginning) {
        ArrayList<String> beginningTokens = tokenize(beginning);
        if (beginningTokens.size() > tokens.size())
            return false;
        for (int i = 0; i < beginningTokens.size(); i++) {
            if (!beginningTokens.get(i).equals(tokens.get(i))) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }
}
