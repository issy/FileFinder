package com.issy.compiler;

public record TokenContext(Token token, String value, Integer line, Integer column) {

  public static TokenContext withPosition(Token token, String value, Lexer.Position position) {
    return new TokenContext(token, value, position.line(), position.column());
  }

  public String getFormattedPosition() {
    return String.format("%d:%d", line + 1, column);
  }

}
