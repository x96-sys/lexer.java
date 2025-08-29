package org.x96.sys.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.buzz.lexer.BuzzLex;
import org.x96.sys.buzz.lexer.visitor.BuzzVisitorMismatch;
import org.x96.sys.lexer.token.Token;
import org.x96.sys.lexer.tokenizer.Tokenizer;
import org.x96.sys.lexer.visitor.Visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LexerMinimalTest {

    public static class FailingVisitor extends Visitor {
        public FailingVisitor(Tokenizer tokenizer) {
            super(tokenizer);
        }

        @Override
        public Token[] safeVisit() throws BuzzVisitorMismatch {
            throw new BuzzVisitorMismatch(this, tokenizer);
        }

        @Override
        public boolean allowed() {
            return true;
        }
    }

    @Test
    void testAllCoreFunctionality() throws IOException {
        byte[] payload = "test".getBytes();
        Lexer lexer = new Lexer(TerminalVisitor.class);
        Token[] tokens = lexer.lex(payload);
        assertNotNull(tokens, "lex() deve retornar tokens");

        tokens = lexer.lexWrapped(payload);
        assertNotNull(tokens, "lexWrapped() deve retornar tokens");

        byte[] emptyPayload = new byte[0];

        assertThrows(
                Exception.class,
                () -> {
                    lexer.lex(emptyPayload);
                });

        tokens = lexer.lexWrapped(emptyPayload);
        assertNotNull(tokens, "lexWrapped deve funcionar com payload vazio");
    }

    @Test
    void testBuzzLexException() throws IOException {
        Path testFile = Path.of("docs/grammar/zero.cs");
        byte[] payload =
                Files.exists(testFile) ? Files.readAllBytes(testFile) : "test data".getBytes();

        Lexer lexer = new Lexer(FailingVisitor.class);

        assertThrows(
                BuzzLex.class,
                () -> {
                    lexer.lex(payload);
                },
                "Deve lançar BuzzLex quando há BuzzVisitorMismatch");
    }
}
