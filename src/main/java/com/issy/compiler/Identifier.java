package com.issy.compiler;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public enum Identifier {
  HAS_FILE_EXTENSION("hasFileExtension", true),
  IS_WITHIN_DIRECTORY("isWithinDirectory", true),
  FILE_CONTAINS("fileContains", true),
  IS_IN_BASE_DIRECTORY("isInBaseDirectory", false);

  private final String name;
  private final boolean takesArgument;

  Identifier(String name, boolean takesArgument) {
    this.takesArgument = takesArgument;
    this.name = name;
  }

  public static Optional<Identifier> getIdentifier(final String identifierName) {
    return Arrays.stream(Identifier.values()).filter(identifier -> Objects.equals(identifier.getName(), identifierName)).findFirst();
  }

  public String getName() {
    return name;
  }

  public boolean getTakesArgument() {
    return takesArgument;
  }

  public Predicate<String> implement() {
    if (takesArgument) {
      throw new RuntimeException("This function should take an argument");
    }
    return switch (this) {
      case IS_IN_BASE_DIRECTORY -> filepath -> !filepath.contains("/");
      default -> throw new RuntimeException("Function not implemented");
    };
  }

  public Predicate<String> implement(String arg) {
    if (!takesArgument) {
      throw new RuntimeException("This function does not take an argument");
    }
    return switch (this) {
      case HAS_FILE_EXTENSION -> filepath -> filepath.endsWith(arg);
      case IS_WITHIN_DIRECTORY -> filepath -> filepath.startsWith(arg);
      default -> throw new RuntimeException("Function not implemented");
    };
  }

}
