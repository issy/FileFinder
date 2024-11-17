package com.issy;

import com.issy.compiler.FileMatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Walker {

  public List<String> walk(String startPath, FileMatcher fileMatcher) {
    try (Stream<Path> pathStream = Files.walk(Path.of(startPath))) {
      return pathStream.map(Path::toString).filter(fileMatcher::matches).toList();
    } catch (IOException e) {
      throw new RuntimeException("Error reading file: " + e.getMessage());
    } catch (SecurityException e) {
      throw new RuntimeException("Not permitted to read file: " + e.getMessage());
    }
  }

}
