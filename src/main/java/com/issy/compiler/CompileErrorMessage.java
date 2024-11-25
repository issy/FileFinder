package com.issy.compiler;

// TODO: Create wrapper class that embeds position data for error message
public enum CompileErrorMessage {
  EMPTY_INPUT("Empty input"),
  MUST_END_WITH_CLOSING_PAREN("Must end in closing parenthesis"),
  MUST_HAVE_MATCHING_PAREN("Must have matching parentheses"),
  TOKEN_NOT_ALLOWED("Token not allowed"),
  INVALID_FUNCTION_NAME("Invalid function name"),
  FUNCTION_NOT_CALLED("Function must be called"),
  FUNCTION_REQUIRES_ARGUMENT("Function requires argument"),
  EMPTY_EXPRESSION("Empty expression not allowed");

  private final String humanReadableMessage;

  CompileErrorMessage(String humanReadableMessage) {
    this.humanReadableMessage = humanReadableMessage;
  }

  public String getHumanReadableMessage() {
    return humanReadableMessage;
  }
}
