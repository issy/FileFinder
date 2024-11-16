package com.issy.compiler;

import java.util.Optional;

public record CompilerResult(Optional<FileMatcher> fileMatcher, Optional<CompileErrorMessage> compileError) {

  public static CompilerResult error(CompileErrorMessage errorMessage) {
    return new CompilerResult(Optional.empty(), Optional.of(errorMessage));
  }

  public static CompilerResult success(FileMatcher fileMatcher) {
    return new CompilerResult(Optional.of(fileMatcher), Optional.empty());
  }

  public boolean isSuccessful() {
    return fileMatcher.isPresent() && compileError.isEmpty();
  }

}
