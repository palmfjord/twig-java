package org.twig4j.core.functional;

import org.junit.Assert;
import org.junit.Test;
import org.twig4j.core.exception.Twig4jException;
import org.twig4j.core.exception.Twig4jRuntimeException;
import org.twig4j.core.template.Context;

import java.util.HashMap;

public class RenderLogicTests extends FunctionalTests {
    @Test
    public void canRenderPlainBoolean() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ true }}");
        setupEnvironment(templates);

        Assert.assertEquals("Bool should render text bool", "true", environment.render("foo.twig"));
    }

    @Test
    public void canRenderPlainInverseBoolean() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ not true }}");
        setupEnvironment(templates);

        Assert.assertEquals("Bool should render inversed text bool", "false", environment.render("foo.twig"));
    }

    @Test
    public void canRenderComparedBoolean() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ true == false }}");
        setupEnvironment(templates);

        Assert.assertEquals("Bool should render text bool", "false", environment.render("foo.twig"));
    }

    @Test
    public void canCompareBoolVars() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ foo == false }}");
        setupEnvironment(templates);

        Context ctx = new Context();
        ctx.put("foo", false);

        Assert.assertEquals("Bool should render text bool", "true", environment.render("foo.twig", ctx));
    }

    @Test
    public void canCompareStringVars() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ foo == \"bar\" }}");
        setupEnvironment(templates);

        Context ctx = new Context();
        ctx.put("foo", "bar");

        Assert.assertEquals("Bool should render text bool", "true", environment.render("foo.twig", ctx));
    }

    @Test
    public void canDoStartsWith() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ 'foobar' starts with 'foo' }}");
        setupEnvironment(templates);

        Assert.assertEquals("Starts with should be true if string starts with foo", "true", environment.render("foo.twig"));
    }

    @Test
    public void canDoEndsWith() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ 'foobar' ends with 'bar' }}");
        setupEnvironment(templates);

        Assert.assertEquals("Starts with should be true if string ends with bar", "true", environment.render("foo.twig"));
    }

    @Test
    public void matches() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("caseSensitive.twig", "{{ 'Foobar' matches '/^[a-z]+/' }}");
        templates.put("caseInsensitive.twig", "{{ 'Foobar' matches '/^[a-z]+/i' }}");
        setupEnvironment(templates);

        Assert.assertEquals("Case sensitive regex shouldn't match string with wrong case", "false", environment.render("caseSensitive.twig"));
        Assert.assertEquals("Case sensitive regex should match string with wrong case", "true", environment.render("caseInsensitive.twig"));
    }

    @Test
    public void canDoIn() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("trueString.twig", "{{ 'foo' in 'foobar' }}");
        templates.put("trueArray.twig", "{{ 'foo' in ['foo', 'bar'] }}");
        templates.put("falseString.twig", "{{ 'nope' in 'foobar' }}");
        templates.put("falseArray.twig", "{{ 'nope' in ['foo', 'bar'] }}");
        templates.put("falseInverseString.twig", "{{ 'foo' not in 'foobar' }}");
        templates.put("trueInverseArray.twig", "{{ 'nope' not in ['foo', 'bar'] }}");
        setupEnvironment(templates);

        Assert.assertEquals("Can find if string is present in string", "true", environment.render("trueString.twig"));
        Assert.assertEquals("Can find if string is present in array", "true", environment.render("trueArray.twig"));
        Assert.assertEquals("Can't find if string is not present in string", "false", environment.render("falseString.twig"));
        Assert.assertEquals("Can't find if string is not present in array", "false", environment.render("falseArray.twig"));
        Assert.assertEquals("Can find if string is not present in string when actually is", "false", environment.render("falseInverseString.twig"));
        Assert.assertEquals("Can find if string is not present in array when is not", "true", environment.render("trueInverseArray.twig"));
    }

    @Test
    public void canRenderNotComparedBoolean() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ true != false }}");
        setupEnvironment(templates);

        Assert.assertEquals("Bool should render text bool", "true", environment.render("foo.twig"));
    }

    @Test
    public void canDoOr() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ false or true }}");
        setupEnvironment(templates);

        Assert.assertEquals("\"false or true\" should be true", "true", environment.render("foo.twig"));
    }

    @Test
    public void canDoAnd() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("foo.twig", "{{ false and true }}");
        setupEnvironment(templates);

        Assert.assertEquals("\"false and true\" should be false", "false", environment.render("foo.twig"));
    }

    @Test
    public void canDoLessThan() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("true.twig", "{{ 1 < 2 }}");
        templates.put("false.twig", "{{ 2 < 1 }}");
        setupEnvironment(templates);

        Assert.assertEquals("2 < 1 should be false", "false", environment.render("false.twig"));
        Assert.assertEquals("1 < 2 should be true", "true", environment.render("true.twig"));
    }

    @Test
    public void canDoGreaterThan() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("true.twig", "{{ 2 > 1 }}");
        templates.put("false.twig", "{{ 1 > 2 }}");
        setupEnvironment(templates);

        Assert.assertEquals("2 > 1 should be true", "true", environment.render("true.twig"));
        Assert.assertEquals("1 > 2 should be false", "false", environment.render("false.twig"));
    }

    @Test
    public void canDoGreaterThanOrEqual() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("true.twig", "{{ 1 >= 1 }}");
        templates.put("false.twig", "{{ 1 >= 2 }}");
        setupEnvironment(templates);

        Assert.assertEquals("1 >= 1 should be true", "true", environment.render("true.twig"));
        Assert.assertEquals("1 >= 2 should be false", "false", environment.render("false.twig"));
    }

    @Test
    public void canDoLessThanOrEqual() throws Twig4jException {
        HashMap<String, String> templates = new HashMap<>();
        templates.put("true.twig", "{{ 1 <= 1 }}");
        templates.put("false.twig", "{{ 2 <= 1 }}");
        setupEnvironment(templates);

        Assert.assertEquals("1 <= 1 should be true", "true", environment.render("true.twig"));
        Assert.assertEquals("2 <= 1 should be false", "false", environment.render("false.twig"));
    }
}
