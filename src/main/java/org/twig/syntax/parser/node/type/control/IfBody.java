package org.twig.syntax.parser.node.type.control;

import org.twig.syntax.parser.node.Node;

public class IfBody extends Node {
    public IfBody(Node conditional, Node body, Integer line) {
        super(line);

        nodes.add(0, conditional);
        nodes.add(1, body);
    }
}
