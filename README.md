# File Finder

This is a utility for writing, building and executing scripts for finding files in your local directories that match specific parameters.

## Features

- [ ] CLI
  - [ ] Parse args
  - [ ] Read input from filepath
  - [ ] Read input from STDIN
- [X] Lexer
  - [X] Implement all tokens
  - [X] Parse string literals
- [ ] Compiler
  - [X] Check token validity
  - [ ] Implement parsed functions
    - [X] `hasFileExtension(extension)`
    - [X] `isWithinDirectory(directory)`
    - [X] `isInBaseDirectory()`
    - [ ] `fileContains(content)`
  - [ ] Perform group optimisation
- [ ] Scanner
  - [ ] Scan from base path
  - [ ] Ignore directories ahead of time
  - [ ] Concurrency
- [ ] Compile to portable executable
- [ ] Publish build artifact
