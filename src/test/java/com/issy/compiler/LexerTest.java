package com.issy.compiler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.issy.compiler.Token.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LexerTest {

  @Test
  void canParseStringLiteral() {
    assertThat(Lexer.parseStringLiteral("\"hello world\"").equals("hello world")).isTrue();
  }

  @Test
  void requiresQuotesToParseString() {
    assertThrows(IllegalArgumentException.class, () -> Lexer.parseStringLiteral("hello world"));
  }

  @Test
  void willNotParseEmptyStringLiteral() {
    assertThrows(IllegalArgumentException.class, () -> Lexer.parseStringLiteral(""));
  }

  @Test
  void canCollectString() {
    // Given
    String input = "my input hello(world)";
    Lexer lexer = new Lexer(input);

    // When
    String collectedResult = lexer.collectUntil((index) -> String.valueOf(input.charAt(index + 1)).equals(" "));

    // Then
    assertThat(collectedResult).isEqualTo("my");
  }

  @Test
  void canLexBasicInput() {
    // Given
    String input = "hasFileExtension(\".js\") || hasFileExtension(\".ts\")";
    Lexer lexer = new Lexer(input);

    // When
    List<TokenContext> tokens = lexer.parse();

    // Then
    assertThat(tokens).map(TokenContext::token).containsExactly(IDENTIFIER, OPEN_PAREN, STRING, CLOSE_PAREN, OR, IDENTIFIER, OPEN_PAREN, STRING, CLOSE_PAREN);
  }

}
