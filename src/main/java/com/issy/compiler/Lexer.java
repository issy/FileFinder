package com.issy.compiler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.issy.compiler.Token.*;

public class Lexer {

  private final String input;
  private final Cursor cursor;

  public Lexer(String input) {
    this.input = input;
    this.cursor = new Cursor(input.length() - 1);
  }

  public List<TokenContext> parse() {
    final List<TokenContext> tokens = new ArrayList<>();
    while (cursor.getValue() < input.length()) {
      String currentChar = charAt(cursor.getValue());
      if (currentChar.equals("\"")) {
        String munched = collectUntil(stringLiteralTerminationPredicate(cursor.getValue()));
        tokens.add(new TokenContext(STRING, parseStringLiteral(munched)));
        cursor.increment();
      } else if (currentChar.equals("(")) {
        tokens.add(new TokenContext(OPEN_PAREN, "("));
        cursor.increment();
      } else if (currentChar.equals(")")) {
        tokens.add(new TokenContext(CLOSE_PAREN, ")"));
        cursor.increment();
      } else if (currentChar.equals("!")) {
        tokens.add(new TokenContext(NOT, "!"));
        cursor.increment();
      } else if (currentChar.equals("&")) {
        if (charAt(cursor.increment()).equals("&")) {
          tokens.add(new TokenContext(OR, "&&"));
        } else {
          throw new RuntimeException("Expected &&");
        }
        cursor.increment();
      } else if (currentChar.equals("|")) {
        if (charAt(cursor.increment()).equals("|")) {
          tokens.add(new TokenContext(OR, "||"));
        } else {
          throw new RuntimeException("Expected ||");
        }
        cursor.increment();
      } else if (Character.isLetter(input.charAt(cursor.getValue()))) {
        tokens.add(new TokenContext(IDENTIFIER, collectUntil(identifierNameTerminationPredicate())));
      } else if (currentChar.equals(" ") || currentChar.equals("\n") || currentChar.equals("\t") || currentChar.equals("\r")) {
        cursor.increment();
        continue;
      } else {
        throw new RuntimeException(String.format("Illegal character %s at %d", currentChar, cursor.getValue()));
      }
    }
    return tokens;
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
    while (!stop.test(cursor.getValue())) {
      cursor.increment();
      stringBuilder.append(charAt(cursor.getValue()));
    }
    return stringBuilder.toString();
  }

  private Predicate<Integer> stringLiteralTerminationPredicate(int currentIndex) {
    return index -> index != currentIndex && charAt(index).equals("\"") && !charAt(index - 1).equals("\\");
  }

  private Predicate<Integer> identifierNameTerminationPredicate() {
    return index -> !Character.isLetter(input.charAt(index));
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

    public Cursor createTemporaryCursor() {
      return new Cursor(value, limit);
    }

    public int getValue() {
      return value;
    }

    public int increment() {
      value++;
      return value;
    }

    public boolean atLimit() {
      return value >= limit;
    }
  }

}
