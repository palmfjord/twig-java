package org.twig4j.core.extension;

import org.twig4j.core.exception.Twig4jRuntimeException;
import org.twig4j.core.filter.Filter;
import org.twig4j.core.syntax.operator.*;
import org.twig4j.core.syntax.parser.tokenparser.*;
import org.twig4j.core.syntax.parser.tokenparser.Set;

import java.util.*;

public class Core implements Extension {
    @Override
    public List<AbstractTokenParser> getTokenParsers() {
        List<AbstractTokenParser> tokenParsers = new ArrayList<>();
        tokenParsers.add(new If());
        tokenParsers.add(new Set());
        tokenParsers.add(new For());
        tokenParsers.add(new Include());
        tokenParsers.add(new Extends());
        tokenParsers.add(new Block());

        return tokenParsers;
    }

    @Override
    public Map<String, Operator> getUnaryOperators() {
        LinkedHashMap<String, Operator> operators = new LinkedHashMap<>();

        operators.put("not", new UnaryNot());

        return operators;
    }

    @Override
    public Map<String, Operator> getBinaryOperators() {
        LinkedHashMap<String, Operator> operators = new LinkedHashMap<>();

        operators.put("or", new BinaryOr());
        operators.put("and", new BinaryAnd());
        operators.put("<=", new BinaryLessThanOrEqual());
        operators.put(">=", new BinaryGreaterThanOrEqual());
        operators.put("<", new BinaryLessThan());
        operators.put(">", new BinaryGreaterThan());
        operators.put("==", new BinaryEquals());
        operators.put("!=", new BinaryNotEquals());
        operators.put("??", new BinaryNullCoalesce());
        operators.put("in", new BinaryIn());
        operators.put("not in", new BinaryNotIn());
        operators.put("starts with", new BinaryStartsWith());
        operators.put("ends with", new BinaryEndsWith());
        operators.put("matches", new BinaryMatches());
        operators.put("..", new BinaryRange());
        operators.put("+", new BinaryAdd());
        operators.put("-", new BinarySubtract());
        operators.put("~", new BinaryConcat());
        operators.put("**", new BinaryPowerOf());
        operators.put("*", new BinaryMultiply());
        operators.put("//", new BinaryFloorDivide());
        operators.put("/", new BinaryDivide());
        operators.put("%", new BinaryMod());

        return operators;
    }

    @Override
    public Map<String, Filter> getFilters() throws Twig4jRuntimeException {
        Map<String, Filter> filters = new HashMap<>();

        try {
            filters.put("upper", new Filter("upper", CoreFilters.class.getMethod("upper", String.class)));
            filters.put("lower", new Filter("lower", CoreFilters.class.getMethod("lower", String.class)));
            filters.put("join", new Filter("join", CoreFilters.class.getMethod("join", List.class, String.class)));
        } catch (NoSuchMethodException e) {
            throw new Twig4jRuntimeException("Invalid core filter specified", e);
        }

        return filters;
    }

    @Override
    public String getName() {
        return "Twig Core";
    }

    /**
     * Make sure it's possible to iterate over the passed object, otherwise return an empty list
     *
     * @param object The object to ensure iterable
     *
     * @return An iterable object
     */
    public static Iterable<?> ensureIterable(Object object) {
        if (object instanceof Object && object.getClass().isArray()) {
            return Arrays.asList((Object[])object);
        }

        if (object instanceof Iterable) {
            return (Iterable)object;
        }

        if (object instanceof Map) {
            return ((Map) object).entrySet();
        }

        return new ArrayList<>();
    }

    public static Boolean inFilter(String needle, String haystack) {
        return haystack.contains(needle);
    }

    public static Boolean inFilter(Object needle, List<?> haystack) {
        return haystack.contains(needle);
    }
}
