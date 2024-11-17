package com.issy.compiler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.function.Predicate;

public class Lexer {

  private final String input;
  private final Cursor cursor;

  public Lexer(String input) {
    this.input = input;
    this.cursor = new Cursor(input.length() - 1);
  }

  public List<TokenContext> lexInput() {
    return List.of();
  }

  public static String parseStringLiteral(String input) {
    try {
      return new ObjectMapper().readValue(input, String.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Invalid JSON string literal: " + input, e);
    }
  }

  public String collectUntil(Predicate<Integer> stop) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(charAt(cursor.getValue()));
    while (!stop.test(cursor.increment())) {
      stringBuilder.append(charAt(cursor.getValue()));
    }
    return stringBuilder.toString();
  }

  private Predicate<Integer> makeIntPredicate(Predicate<String> predicate) {
    return index -> predicate.test(charAt(index));
  }

  private Predicate<Integer> stringLiteralTerminationPredicate() {
    return index -> charAt(index).equals("\"") && !charAt(index - 1).equals("\\");
  }

  private String charAt(int index) {
    return String.valueOf(input.charAt(index));
  }

  private static class Cursor {

    private int value;
    private final int limit;

    private Cursor(int value, int limit) {
      this.value = value;
      this.limit = limit;
    }

    public Cursor(int limit) {
      this.limit = limit;
      this.value = 0;
    }

    public Cursor temp() {
      return new Cursor(value, limit);
    }

    public int getValue() {
      return value;
    }

    public int increment() {
      value++;
      return value;
    }
  }

}
