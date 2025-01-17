package com.issy.compiler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CompilerTest {

  @Test
  void canCompile() {
    // Given
    final List<TokenContext> tokenContexts = List.of(
        new TokenContext(Token.IDENTIFIER, Identifier.HAS_FILE_EXTENSION.getName(), 0, 0),
        new TokenContext(Token.OPEN_PAREN, "(", 0, 0),
        new TokenContext(Token.STRING, "\".tsx\"", 0, 0),
        new TokenContext(Token.CLOSE_PAREN, ")", 0, 0),
        new TokenContext(Token.AND, "&&", 0, 0),
        new TokenContext(Token.IDENTIFIER, Identifier.IS_IN_BASE_DIRECTORY.getName(), 0, 0),
        new TokenContext(Token.OPEN_PAREN, "(", 0, 0),
        new TokenContext(Token.CLOSE_PAREN, ")", 0, 0)
    );
    final Compiler compiler = new Compiler(tokenContexts);

    // When
    final CompilerResult compilerResult = compiler.compile();

    // Then
    assertThat(compilerResult.isSuccessful()).isTrue();
  }

  @Test
  void returnsErrorForEmptyInput() {
    // Given
    final List<TokenContext> tokenContexts = List.of();
    final Compiler compiler = new Compiler(tokenContexts);

    // When
    final CompilerResult compilerResult = compiler.compile();

    // Then
    assertThat(compilerResult.isSuccessful()).isFalse();
    assertThat(compilerResult.compileError()).hasValue(CompileErrorMessage.EMPTY_INPUT);
  }

}
