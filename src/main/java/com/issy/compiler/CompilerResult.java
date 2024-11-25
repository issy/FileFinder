package com.issy.compiler;

import java.util.Optional;
import java.util.function.Predicate;

public record CompilerResult(Optional<Predicate<String>> fileMatcher, Optional<CompileErrorMessage> compileError) {

  public static CompilerResult error(CompileErrorMessage errorMessage) {
    return new CompilerResult(Optional.empty(), Optional.of(errorMessage));
  }

  public static CompilerResult success(Predicate<String> fileMatcher) {
    return new CompilerResult(Optional.of(fileMatcher), Optional.empty());
  }

  public boolean isSuccessful() {
    return fileMatcher.isPresent() && compileError.isEmpty();
  }

}
