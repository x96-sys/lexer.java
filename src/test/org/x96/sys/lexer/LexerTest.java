package org.x96.sys.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.lexer.token.Kind;
import org.x96.sys.lexer.token.Token;
import org.x96.sys.lexer.visitor.Visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LexerTest {

    public static Token[] lex(Class<? extends Visitor> v, byte[] payload) {
        Lexer lexer = new Lexer(v);
        return lexer.lex(payload);
    }

    @Test
    void happyWrapped() {
        byte[] payload = "".getBytes();
        Lexer lexer = new Lexer(TerminalVisitor.class);
        Token[] tokens = lexer.lexWrapped(payload);
        assertEquals(1, tokens.length);
        assertEquals(Kind.STX, tokens[0].kind());
    }

    @Test
    void happyFollow() throws IOException {
        Path path = Path.of("docs/grammar/zero.cs");
        byte[] bytes = Files.readAllBytes(path);
        Lexer lexer = new Lexer(TerminalFollowVisitor.class);
        Token[] t = lexer.lexWrapped(bytes);
        assertEquals(2221, t.length);
    }
}
