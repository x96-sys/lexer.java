package org.x96.sys.lexer;

import org.x96.sys.lexer.tokenizer.Tokenizer;
import org.x96.sys.lexer.visitor.Visitor;

public class TerminalVisitor extends Visitor {
    public TerminalVisitor(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return true;
    }
}
