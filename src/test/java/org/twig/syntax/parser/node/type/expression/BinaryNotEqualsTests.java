package org.twig.syntax.parser.node.type.expression;

import org.junit.Assert;
import org.junit.Test;
import org.twig.Environment;
import org.twig.compiler.ClassCompiler;
import org.twig.exception.LoaderException;
import org.twig.exception.TwigRuntimeException;
import org.twig.syntax.parser.node.Node;

public class BinaryNotEqualsTests {
    @Test
    public void testCompile() throws LoaderException, TwigRuntimeException {
        ClassCompiler compiler = new ClassCompiler(new Environment());
        Node left = new Constant("5", 1);
        Node right = new Constant("2", 1);
        BinaryNotEquals notEqualsNode = new BinaryNotEquals(left, right, 1);

        notEqualsNode.compile(compiler);

        Assert.assertEquals(
                "Compiled source should cast left to Object and compare it to right with not operator",
                "(!compare(5, 2))",
                compiler.getSourceCode()
        );
    }
}