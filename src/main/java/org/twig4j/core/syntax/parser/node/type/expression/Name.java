package org.twig4j.core.syntax.parser.node.type.expression;

import org.twig4j.core.compiler.ClassCompiler;
import org.twig4j.core.exception.LoaderException;
import org.twig4j.core.exception.Twig4jRuntimeException;

public class Name extends Expression {
    public Name(String name, Integer line) {
        super(line);
        putAttribute("name", name);
    }

    @Override
    public void compile(ClassCompiler compiler) throws LoaderException, Twig4jRuntimeException {
        compiler.writeRaw("getContext(context, \"" + getAttribute("name") + "\", false, " + getLine() +")");
    }
}
