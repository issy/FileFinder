package com.issy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Walker {

  public List<String> walk(String startPath, Predicate<String> fileMatcher) {
    try (Stream<Path> pathStream = Files.walk(Path.of(startPath))) {
      return pathStream.map(Path::toString).filter(fileMatcher).toList();
    } catch (IOException e) {
      throw new RuntimeException("Error reading file: " + e.getMessage());
    } catch (SecurityException e) {
      throw new RuntimeException("Not permitted to read file: " + e.getMessage());
    }
  }

}
