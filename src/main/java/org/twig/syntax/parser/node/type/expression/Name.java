package org.twig.syntax.parser.node.type.expression;

import org.twig.compiler.ClassCompiler;
import org.twig.exception.LoaderException;

public class Name extends Expression {
    public Name(String name, Integer line) {
        super(line);
        putAttribute("name", name);
    }

    @Override
    public void compile(ClassCompiler compiler) throws LoaderException {
        compiler
                .addDebugInfo(this)
                .writeLine("getContext(context, \"" + getAttribute("name") + "\", false, " + getLine() +");");
    }
}
