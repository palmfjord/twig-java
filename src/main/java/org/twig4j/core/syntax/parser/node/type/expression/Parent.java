package org.twig4j.core.syntax.parser.node.type.expression;

import org.twig4j.core.compiler.ClassCompiler;
import org.twig4j.core.exception.LoaderException;
import org.twig4j.core.exception.Twig4jRuntimeException;

public class Parent extends Expression {
    public Parent(Integer line) {
        super(line);
    }

    public Parent(String name, Integer line) {
        super(line);

        putAttribute("output", false);
        putAttribute("name", name);
    }

    public void compile(ClassCompiler compiler) throws LoaderException, Twig4jRuntimeException {
        compiler.writeRaw("displayParentBlock(\"" + getAttribute("name") + "\", context, blocks)");
    }
}
