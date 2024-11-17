package com.issy;

import com.issy.compiler.Compiler;
import com.issy.compiler.CompilerResult;
import com.issy.compiler.Lexer;
import com.issy.compiler.TokenContext;
import org.apache.commons.cli.ParseException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    try {
      // Parse args
      InputArgs parsedArgs = new CommandArgsParser().parse(args);
      Optional<String> input = parsedArgs.inputFilepath().isPresent() ? InputProvider.fromFilepath(parsedArgs.inputFilepath().get()) : InputProvider.fromStdin();
      // Lex input
      Lexer lexer = new Lexer(input.orElseThrow());
      List<TokenContext> tokens = lexer.parse();
      // Compile tokens
      Compiler compiler = new Compiler(tokens);
      CompilerResult compilerResult = compiler.compile();
      if (!compilerResult.isSuccessful()) {
        logger.info("Compilation failed: " + compilerResult.compileError());
      }
      // TODO: Scan file tree
    } catch (ParseException e) {
      logger.severe("Error parsing arguments: " + e.getMessage());
      System.exit(1);
    }
  }
}
