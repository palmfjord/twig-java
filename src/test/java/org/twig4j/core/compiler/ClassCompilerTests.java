package org.twig4j.core.compiler;

import org.junit.Assert;
import org.junit.Test;
import org.twig4j.core.Environment;
import org.twig4j.core.exception.LoaderException;
import org.twig4j.core.exception.Twig4jRuntimeException;
import org.twig4j.core.syntax.parser.node.Node;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ClassCompilerTests {
    @Test
    public void canWriteAndGetSourceCode() {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());
        String code = "import com.twig4j.something;";
        classCompiler.write(code);

        Assert.assertEquals("Written code is equal to written source code", code, classCompiler.getSourceCode());

        // Start fresh since we dont want the old source code
        classCompiler = new ClassCompiler(new Environment());
        classCompiler.writeLine(code);

        Assert.assertEquals("Written line should end with newline", code.concat("\n"), classCompiler.getSourceCode());
    }

    @Test
    public void canWriteRaw() {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());
        classCompiler.indent();
        classCompiler.writeRaw("foo");

        Assert.assertEquals("Written code should not be indented", "foo", classCompiler.getSourceCode());
    }

    @Test
    public void canIndent() {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());
        String code = "import com.twig4j.something";

        classCompiler
                .indent()
                .write(code);

        Assert.assertEquals("Indented code is equal to 4 spaces + written line", "    ".concat(code), classCompiler.getSourceCode());

        // Start fresh since we dont want the old source code
        classCompiler = new ClassCompiler(new Environment());
        classCompiler
                .indent()
                .unIndent()
                .write(code);

        Assert.assertEquals("Indented and then unIndented code is equal to written line", code, classCompiler.getSourceCode());
    }

    @Test
    public void canSubCompile() throws LoaderException, Twig4jRuntimeException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());
        Node nodeStub = mock(Node.class);

        classCompiler.subCompile(nodeStub);

        verify(nodeStub).compile(classCompiler);
    }

    @Test
    public void canCompile() throws LoaderException, Twig4jRuntimeException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());
        Node nodeStub = mock(Node.class);
        classCompiler.compile(nodeStub);

        verify(nodeStub).compile(classCompiler);
    }

    @Test
    public void canWriteString() {
        ClassCompiler compiler = new ClassCompiler(new Environment());

        Assert.assertEquals(
                "String should be a java string",
                "\"foo\"",
                compiler.writeString("foo").getSourceCode()
        );
    }

    @Test
    public void canWriteStringWithQuotes() {
        ClassCompiler compiler = new ClassCompiler(new Environment());
        compiler.writeString("foo \"bar\"");

        Assert.assertEquals(
                "String with quotes should be escaped",
                "\"foo \\\"bar\\\"\"",
                compiler.getSourceCode()
        );
    }

    @Test
    public void canCWriteStringWithBackslashAndQuotes() {
        ClassCompiler compiler = new ClassCompiler(new Environment());
        compiler.writeString("foo \\\"bar\"");

        Assert.assertEquals(
                "String with backslashes and quotes should be escaped",
                "\"foo \\\\\\\"bar\\\"\"",
                compiler.getSourceCode()
        );
    }

    @Test
    public void canWriteStringWithNewline() {
        ClassCompiler compiler = new ClassCompiler(new Environment());
        compiler.writeString("foo\nbar");

        Assert.assertEquals("String should contain a \\n where newline was", "\"foo\\nbar\"", compiler.getSourceCode());
    }

    @Test
    public void canRepresentString() throws LoaderException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.representValue("a string");
        Assert.assertEquals(
                "Represent value should have compiled a string",
                "\"a string\"",
                classCompiler.getSourceCode()
        );
    }

    @Test
    public void canRepresentInteger() throws LoaderException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.representValue(1);
        Assert.assertEquals(
                "Represent value should have compiled an integer",
                "1",
                classCompiler.getSourceCode()
        );
    }

    @Test
    public void canRepresentNull() throws LoaderException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.representValue(null);
        Assert.assertEquals(
                "Represent value should have compiled null",
                "null",
                classCompiler.getSourceCode()
        );
    }

    @Test
    public void canRepresentTrue() throws LoaderException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.representValue(true);
        Assert.assertEquals(
                "Represent value should have compiled true",
                "true",
                classCompiler.getSourceCode()
        );
    }

    @Test
    public void canRepresentFalse() throws LoaderException {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.representValue(false);
        Assert.assertEquals(
                "Represent value should have compiled false",
                "false",
                classCompiler.getSourceCode()
        );
    }

    @Test
    public void canAddDebugInfo() {
        ClassCompiler classCompiler = new ClassCompiler(new Environment());

        classCompiler.indent().addDebugInfo(new Node(new ArrayList<Node>(), new HashMap<String, Object>(), 1337, "Idk"));

        Assert.assertEquals(
                "Add debug info should write an indented line comment with the line number",
                "    // line 1337\n",
                classCompiler.getSourceCode()
        );
    }
}
