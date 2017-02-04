package org.twig4j.core.syntax.parser.node.type;

import org.junit.Test;
import org.twig4j.core.compiler.ClassCompiler;
import org.twig4j.core.exception.LoaderException;
import org.twig4j.core.exception.Twig4jRuntimeException;
import org.twig4j.core.syntax.parser.node.type.expression.Constant;

import static org.mockito.Mockito.*;

public class PrintExpressionTests {
    @Test
    public void canCompile() throws LoaderException, Twig4jRuntimeException {
        ClassCompiler compiler = mock(ClassCompiler.class);
        Constant constant = new Constant(1);
        constant.addNode(constant);
        PrintExpression print = new PrintExpression(constant, 1);

        when(compiler.addDebugInfo(anyObject())).thenReturn(compiler);
        when(compiler.write(anyString())).thenReturn(compiler);
        when(compiler.writeRaw(anyString())).thenReturn(compiler);
        when(compiler.subCompile(anyObject())).thenReturn(compiler);

        print.compile(compiler);

        verify(compiler).addDebugInfo(print);
        verify(compiler).write("output.append(");
        verify(compiler).subCompile(constant);
        verify(compiler).writeRaw(");\n");
    }
}