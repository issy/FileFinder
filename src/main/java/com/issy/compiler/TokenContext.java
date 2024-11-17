package com.issy.compiler;

// TODO: Include line:col position data for better error messages
public record TokenContext(Token token, String value, Integer line, Integer column) {

  public static TokenContext withPosition(Token token, String value, Lexer.Position position) {
    return new TokenContext(token, value, position.line(), position.column());
  }

  public String getFormattedPosition() {
    return String.format("%d:%d", line + 1, column);
  }

}
