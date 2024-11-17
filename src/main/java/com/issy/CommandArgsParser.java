package com.issy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

public class CommandArgsParser {
  private static final Options options = new Options().addOption("f", "Input file to read from");

  private final DefaultParser parser;

  public CommandArgsParser() {
    this.parser = new DefaultParser();
  }

  public InputArgs parse(String[] args) throws ParseException {
    CommandLine output = parser.parse(options, args);
    return new InputArgs(output.getParsedOptionValue("f"));
  }

  public record InputArgs(Optional<String> inputFilepath) {
  }
}
