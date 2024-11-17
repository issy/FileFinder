package com.issy;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

  @Test
  void canParseArgs() throws ParseException {
    CommandArgsParser argsParser = new CommandArgsParser();
    CommandArgsParser.InputArgs result = argsParser.parse(new String[]{"--f", "foo/bar.txt"});
    assertThat(result.inputFilepath()).hasValue("foo/bar.txt");
  }

}