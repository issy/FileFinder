package com.issy;

import org.apache.commons.cli.*;

import java.util.Optional;

public class CommandArgsParser {
  private static final Options options = new Options()
      .addOption(Option.builder("f")
          .longOpt("file")
          .hasArg()
          .argName("file-path")
          .desc("Specify the input file path")
          .build()
      )
      .addOption(Option.builder("d")
          .longOpt("directory")
          .hasArg()
          .argName("directory")
          .desc("The directory to scan within")
          .build()
      );

  private final DefaultParser parser;

  public CommandArgsParser() {
    this.parser = new DefaultParser();
  }

  public InputArgs parse(String[] args) throws ParseException {
    CommandLine output = parser.parse(options, args);
    Optional<String> fileValue = output.getParsedOptionValue("--file");
    String directoryValue = output.getParsedOptionValue("--directory", "./");
    return new InputArgs(fileValue, directoryValue);
  }

}
