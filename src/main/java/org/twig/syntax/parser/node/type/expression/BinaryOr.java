package org.twig.syntax.parser.node.type.expression;

import org.twig.compiler.ClassCompiler;
import org.twig.syntax.parser.node.Node;

public class BinaryOr extends Binary {
    public BinaryOr(Node left, Node right, Integer line) {
        super(left, right, line);
    }

    @Override
    protected Binary compileOperator(ClassCompiler compiler) {
        compiler.writeRaw("||");

        return this;
    }
}