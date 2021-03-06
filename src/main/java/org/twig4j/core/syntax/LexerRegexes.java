package org.twig4j.core.syntax;

import org.twig4j.core.syntax.operator.Operator;

import java.util.*;
import java.util.regex.Pattern;

public class LexerRegexes {
    private LexerOptions options;
    private Map<String, Operator> unaryOperators;
    private Map<String, Operator> binaryOperators;

    public LexerRegexes(LexerOptions options, Map<String, Operator> unaryOperators, Map<String, Operator> binaryOperators) {
        this.options = options;
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
    }

    public Pattern getLexVariableEnd() {
        String pattern = "^\\s*"
                + Pattern.quote(options.getWhitespaceTrim() + options.getVariableClose())
                + "\\s*|^\\s*"
                + Pattern.quote(options.getVariableClose());

        return Pattern.compile(pattern);
    }

    public Pattern getLexBlockEnd() {
        String pattern = "^\\s*(?:"
                + Pattern.quote(options.getWhitespaceTrim() + options.getBlockClose())
                + "\\s*|^\\s*"
                + Pattern.quote(options.getBlockClose())
                + ")\\n?";

        return Pattern.compile(pattern);
    }

    public Pattern getLexRawData() {
        String pattern = "("
                + Pattern.quote(options.getBlockOpen() + options.getWhitespaceTrim())
                + "|"
                + Pattern.quote(options.getBlockOpen())
                + ")\\s*(?:end%s)\\s*(?:"
                + Pattern.quote(options.getWhitespaceTrim() + options.getBlockClose())
                + "\\s*|\\s*"
                + Pattern.quote(options.getBlockClose())
                + ")";

        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public Pattern getOperator() {
        String pattern = "";

        // TODO

        return Pattern.compile(pattern);

    }

    public Pattern getLexCommentEnd() {
        String pattern = "(?:"
                + Pattern.quote(options.getWhitespaceTrim() + options.getCommentClose())
                + "\\s*|"
                + Pattern.quote(options.getCommentClose())
                + ")\\n?";

        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public Pattern getLexBlockRaw() {
        String pattern = "^\\s*(raw|verbatim)\\s*(?:"
                + Pattern.quote(options.getWhitespaceTrim() + options.getBlockClose())
                + "\\s*|\\s*"
                + Pattern.quote(options.getBlockClose())
                + ")";

        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public Pattern getBlockLine() {
        String pattern = "^\\s*line\\s+(\\d+)\\s*"
                + Pattern.quote(options.getBlockClose());

        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public Pattern getLexTokensStart() {
        String pattern = "("
                + Pattern.quote(options.getVariableOpen())
                + "|" + Pattern.quote(options.getBlockOpen())
                + "|" + Pattern.quote(options.getCommentOpen())
                + ")("
                + Pattern.quote(options.getWhitespaceTrim())
                + ")?";

        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public Pattern getInterpolationStart() {
        String pattern = "^"
                + Pattern.quote(options.getInterpolationOpen())
                + "\\s*";

        return Pattern.compile(pattern);
    }

    public Pattern getInterpolationEnd() {
        String pattern = "^\\s*"
                + Pattern.quote(options.getInterpolationClose());

        return Pattern.compile(pattern);
    }

    public Pattern getPunctuation() {
        String pattern = "^\\s*[" + Pattern.quote("()[]{}?:.,|") + "]";

        return Pattern.compile(pattern);
    }

    /**
     * Get all the operators' regexes
     * @return The operators' regexes
     */
    public Pattern getOperators() {
        StringBuilder pattern = new StringBuilder();
        pattern.append("^(");

        LinkedHashMap<String, Operator> allOperators = new LinkedHashMap<>();
        allOperators.putAll(unaryOperators);
        allOperators.putAll(binaryOperators);

        List<String> operatorSymbols = new ArrayList<>();
        operatorSymbols.addAll(allOperators.keySet());

        // Sort operators by size from longest to shortest to avoid that i.e. "in" does not get detected when using ie the operator "inside"
        Collections.sort(operatorSymbols, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return -1;
                } else if (o1.length() == o2.length()){
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        Iterator<String> it = operatorSymbols.iterator();
        while (it.hasNext()) {
            String operator = it.next();
            pattern.append(Pattern.quote(operator));

            // If the operator ends with a number or character it has to end with spaces
            if (Pattern.compile(".*[\\w\\d]+$").matcher(operator).matches()) {
                pattern.append("(?=\\s+)");
            }

            pattern.append("|");

        }

        // Add equals sign
        pattern.append("=");

        pattern.append(")");

        return Pattern.compile(pattern.toString());
    }

    public Pattern getExpressionName() {
        return Pattern.compile("^[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*");
    }

    public Pattern getExpressionNumber() {
        return Pattern.compile("^[0-9]+(?:\\.[0-9]+)?");
    }

    public Pattern getExpressionString() {
        return Pattern.compile("^\"([^#\"\\\\]*(?:\\\\.[^#\"\\\\]*)*)\"|^'([^'\\\\]*(?:\\\\.[^'\\\\]*)*)'", Pattern.DOTALL);
    }

    public Pattern getDoubleQuoteStringDelimiter() {
        return Pattern.compile("^\"");
    }

    public Pattern getDoubleQuoteStringPart() {
        return Pattern.compile("^[^#\"\\\\]*(?:^(?:^\\\\.|^#(?!\\{))[^#\"\\\\]*)*", Pattern.DOTALL);
    }

    public void setOptions(LexerOptions options) {
        this.options = options;
    }

    public void setUnaryOperators(Map<String, Operator> unaryOperators) {
        this.unaryOperators = unaryOperators;
    }

    public void setBinaryOperators(Map<String, Operator> binaryOperators) {
        this.binaryOperators = binaryOperators;
    }
}
