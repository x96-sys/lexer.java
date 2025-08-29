package org.x96.sys.lexer;

import org.x96.sys.lexer.token.Token;
import org.x96.sys.lexer.tokenizer.Tokenizer;
import org.x96.sys.lexer.visitor.Visitor;

public class TerminalFollowVisitor extends Visitor {
    public TerminalFollowVisitor(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token[] visit() {
        terminalFollow();
        return stream();
    }

    private void terminalFollow() {
        if (tokenizer.ready() && allowed()) {
            rec();
            terminalFollow();
        }
    }

    @Override
    public boolean allowed() {
        return true;
    }
}
