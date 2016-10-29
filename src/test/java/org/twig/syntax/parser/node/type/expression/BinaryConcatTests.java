package org.twig.syntax.parser.node.type.expression;

import org.junit.Test;
import org.twig.compiler.ClassCompiler;
import org.twig.exception.LoaderException;
import org.twig.syntax.parser.node.Node;
import org.twig.syntax.parser.node.type.expression.BinaryConcat;

import static org.mockito.Mockito.*;

public class BinaryConcatTests {
    @Test
    public void testCompile() throws LoaderException {
        ClassCompiler compilerStub = mock(ClassCompiler.class);
        Node left = new Node(1);
        Node right = new Node(2);
        BinaryConcat concatNode = new BinaryConcat(left, right, 1);

        when(compilerStub.writeRaw(anyString())).thenReturn(compilerStub);
        when(compilerStub.subCompile(anyObject())).thenReturn(compilerStub);

        concatNode.compile(compilerStub);

        verify(compilerStub).subCompile(left);
        verify(compilerStub).writeRaw(" + ");
        verify(compilerStub).subCompile(right);
    }
}
