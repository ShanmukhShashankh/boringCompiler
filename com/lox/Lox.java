package com.anotherinterpreter.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
  static boolean hadError = false;

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

    /*
     * EX_DATAERR (65) The input data was incorrect in some way.
     * This should only be used for user's data and not system
     * files.
     */

    if (hadError)
      System.exit(65);
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

      // Reset the flag to not kill the entire REPL session
      hadError = false;
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

  // Error Handling
  static void error(int line, String message) {
    report(line, "", message);
  }

  // Helper function to report error on a given line
  private static void report(int line, String where, String message) {
    System.err.println(
        "[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
}
