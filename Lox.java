package com.anotherinterpreter.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lox {
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      // Wrong Usage
      System.out.println("Usage: jlox [script]");

      /*
       * According to sysexits.h:
       * EX_USAGE(64) The command was used incorrectly, e.g.,
       * with the wrong number of arguments, a bad flag,
       * a bad syntax in a parameter, or whatever.
       */

      System.exit(64);
    } else if (args.length == 1) {
      // Running a File
      runFile(args[0]);
    } else {
      // REPL
      runPrompt();
    }
  }

  private static void runFile(String path) throws IOException {
    // Reading the file as a byte array
    byte[] bytes = Files.readAllBytes(Paths.get(path));

    // Passing the bytearray as string to the run function
    run(new String(bytes, Charset.defaultCharset()));
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    // REPL
    for (;;) {
      System.out.println("> ");
      String line = reader.readLine();
      if (line == null)
        break;
      run(line);
    }
  }

  // Core Function 
  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    for (Token token : tokens) {
      System.out.println(token);
    }
  }
}
