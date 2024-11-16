package com.issy.compiler;

public enum CompileErrorMessage {
  EMPTY_INPUT("Empty input"),
  MUST_END_WITH_CLOSING_PAREN("Must end in closing parenthesis"),
  MUST_HAVE_MATCHING_PAREN("Must have matching parentheses"),
  TOKEN_NOT_ALLOWED("Token not allowed"),
  INVALID_FUNCTION_NAME("Invalid function name"),
  FUNCTION_NOT_CALLED("Function must be called"),
  FUNCTION_REQUIRES_ARGUMENT("Function requires argument");

  private final String humanReadableMessage;

  CompileErrorMessage(String humanReadableMessage) {
    this.humanReadableMessage = humanReadableMessage;
  }

  public String getHumanReadableMessage() {
    return humanReadableMessage;
  }
}
