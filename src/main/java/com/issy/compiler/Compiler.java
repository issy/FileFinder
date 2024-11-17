package com.issy.compiler;

import java.util.List;
import java.util.Optional;

import static com.issy.compiler.CompileErrorMessage.*;
import static com.issy.compiler.Token.*;

public class Compiler {

  private final List<TokenContext> tokens;

  public Compiler(List<TokenContext> tokens) {
    this.tokens = tokens;
  }

  public CompilerResult compile() {
    if (tokens.isEmpty()) {
      return CompilerResult.error(EMPTY_INPUT);
    }

    if (!hasMatchingAmountOfParentheses()) {
      return CompilerResult.error(MUST_HAVE_MATCHING_PAREN);
    }

    for (int index = 0; index < tokens.size(); index++) {
      final TokenContext currentContext = tokens.get(index);
      final Optional<TokenContext> nextContextMaybe = getContextAtIndex(index + 1);
      if (nextContextMaybe.isEmpty()) { // Final token
        if (!currentContext.token().equals(CLOSE_PAREN)) {
          return CompilerResult.error(MUST_END_WITH_CLOSING_PAREN);
        }
        break;
      }

      final TokenContext nextContext = nextContextMaybe.get();
      if (!currentContext.token().nextTokenIsAllowed(nextContext.token())) {
        return CompilerResult.error(TOKEN_NOT_ALLOWED); // TODO: Get error message from somewhere
      }

      if (currentContext.token().equals(Token.IDENTIFIER)) {
        final Optional<CompileErrorMessage> validatedFunctionCall = validateFunctionCall(currentContext, index);
        if (validatedFunctionCall.isPresent()) {
          return CompilerResult.error(validatedFunctionCall.get());
        }
      }
    }
    // TODO: Return success result here
    return CompilerResult.success((s) -> true);
  }

  FileMatcher implementMatcher(int index) {
    TokenContext currentContext = getContextAtIndex(index).orElseThrow();
    Identifier identifier = Identifier.getIdentifier(currentContext.value()).orElseThrow();
    return identifier.getTakesArgument() ? identifier.implement(getContextAtIndex(index + 2).orElseThrow().value()) : identifier.implement();
  }

  boolean hasMatchingAmountOfParentheses() {
    final long openingParenCount = tokens.stream().filter(tokenContext -> tokenContext.token() == OPEN_PAREN).count();
    final long closingParenCount = tokens.stream().filter(tokenContext -> tokenContext.token() == CLOSE_PAREN).count();
    return openingParenCount == closingParenCount;
  }

  Optional<CompileErrorMessage> validateFunctionCall(TokenContext context, int index) {
    final Optional<Identifier> maybeIdentifier = Identifier.getIdentifier(context.value());
    if (maybeIdentifier.isEmpty()) {
      return Optional.of(INVALID_FUNCTION_NAME);
    }
    final Identifier identifier = maybeIdentifier.get();
    final boolean takesArgument = identifier.getTakesArgument();
    if (getContextAtIndex(index + 1).filter(c -> c.token().equals(OPEN_PAREN)).isEmpty()) {
      return Optional.of(FUNCTION_NOT_CALLED);
    }
    if (takesArgument) {
      if (getContextAtIndex(index + 2).filter(c -> c.token().equals(STRING)).isEmpty()) {
        return Optional.of(FUNCTION_REQUIRES_ARGUMENT);
      }
      if (getContextAtIndex(index + 3).filter(c -> c.token().equals(CLOSE_PAREN)).isEmpty()) {
        return Optional.of(MUST_HAVE_MATCHING_PAREN);
      }
    } else {
      if (getContextAtIndex(index + 2).filter(c -> c.token().equals(CLOSE_PAREN)).isEmpty()) {
        return Optional.of(MUST_HAVE_MATCHING_PAREN);
      }
    }
    return Optional.empty();
  }

  Optional<TokenContext> getContextAtIndex(int index) {
    return Optional.ofNullable(index < tokens.size() ? tokens.get(index) : null);
  }

}
