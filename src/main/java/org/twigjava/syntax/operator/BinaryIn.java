package org.twigjava.syntax.operator;

public class BinaryIn implements Operator {
    @Override
    public Integer getPrecedence() {
        return 20;
    }

    @Override
    public Class getNodeClass() {
        return org.twigjava.syntax.parser.node.type.expression.BinaryIn.class;
    }

    @Override
    public Associativity getAssociativity() {
        return Associativity.LEFT;
    }
}