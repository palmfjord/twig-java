package org.twig4j.core.syntax.parser.node.type.expression;

import org.twig4j.core.compiler.ClassCompiler;
import org.twig4j.core.syntax.parser.node.Node;

public class BinaryLessThanOrEqual extends BinaryNumberComparison {
    public BinaryLessThanOrEqual(Node left, Node right, Integer line) {
        super(left, right, line);
    }

    @Override
    protected Binary compileOperator(ClassCompiler compiler) {
        compiler.writeRaw("<=");

        return this;
    }
}
