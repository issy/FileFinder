package com.issy.compiler;

import java.util.Set;

public enum Token {
  // Operators
  NOT,
  AND,
  OR,
  // Grammar
  CLOSE_PAREN,
  STRING,
  OPEN_PAREN,
  IDENTIFIER;

  public boolean nextTokenIsAllowed(final Token token) {
    return getAllowedNextTokens().contains(token);
  }

  public Set<Token> getAllowedNextTokens() {
    return switch (this) {
      case NOT:
        yield Set.of(OPEN_PAREN, IDENTIFIER);
      case AND, OR:
        yield Set.of(OPEN_PAREN, IDENTIFIER, NOT);
      case CLOSE_PAREN:
        yield Set.of(AND, OR);
      case STRING:
        yield Set.of(CLOSE_PAREN);
      case OPEN_PAREN:
        yield Set.of(STRING, CLOSE_PAREN);
      case IDENTIFIER:
        yield Set.of(OPEN_PAREN);
    };
  }

}
