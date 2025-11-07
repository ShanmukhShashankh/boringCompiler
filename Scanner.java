package com.anotherinterpreter.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anotherinterpreter.lox.TokenType.*;

class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();

  // first character in the lexeme being scanned
  private int start = 0;
  // current character being scanned
  private int current = 0;
  // what line current is on
  private int line = 1;

  Scanner(String source){
    this.source = source;
  }

  List<Token> scanTokens(){
    while (!isAtEnd()){
      start = current;
      scanTokens();
    }

    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }

  private boolean isAtEnd(){
    return current >= source.length();
  }
}
