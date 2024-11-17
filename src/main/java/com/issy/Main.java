package com.issy;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    final Options options = new Options();
    options.addOption("f", "Input file to read from");
    final DefaultParser parser = new DefaultParser();
    try {
      var result = parser.parse(options, args);
      logger.info(result.getOptionValue("f"));
    } catch (ParseException e) {
      logger.severe("Error parsing arguments: " + e.getMessage());
      System.exit(1);
    }
  }
}
