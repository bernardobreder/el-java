package org.breder.el;

import java.io.IOException;
import java.io.InputStream;

import org.breder.parser.AbstractLexer;
import org.breder.parser.exception.LexicalException;
import org.breder.parser.exception.ParserException;
import org.breder.parser.token.IdentifyToken;
import org.breder.parser.token.NumberToken;
import org.breder.parser.token.StringToken;
import org.breder.parser.token.Token;
import org.breder.parser.token.WordToken;

/**
 * Realiza o parser lexical
 * 
 * @author Tecgraf
 */
public class ELLexer extends AbstractLexer {

  /** Token Ids */
  public final static int AND = 256, CONTINUE = 257, BREAK = 258, DO = 259,
    ELSE = 260, EQ = 261, FALSE = 262, GE = 263, IF = 265, LE = 267, NE = 269,
    OR = 271, TRUE = 274, WHILE = 295, END = 276, REPEAT = 277, FOR = 278,
    THIS = 291, DEC = 292, INC = 293, NULL = 294;
  /** Token */
  public static final WordToken EQ_TOKEN = new WordToken("==", EQ);
  /** Token */
  public static final WordToken NOT_EQUAL_TOKEN = new WordToken("!=", NE);
  /** Token */
  public static final WordToken LE_TOKEN = new WordToken("<=", LE);
  /** Token */
  public static final WordToken GE_TOKEN = new WordToken(">=", GE);
  /** Token */
  public static final WordToken INC_TOKEN = new WordToken("++", INC);
  /** Token */
  public static final WordToken DEC_TOKEN = new WordToken("--", DEC);

  static {
    WordToken.put(">=", GE_TOKEN);
    WordToken.put("<=", LE_TOKEN);
    WordToken.put("!=", NOT_EQUAL_TOKEN);
    WordToken.put("==", EQ_TOKEN);
    WordToken.put("and", new WordToken("and", AND));
    WordToken.put("false", new WordToken("false", FALSE));
    WordToken.put("or", new WordToken("or", OR));
    WordToken.put("true", new WordToken("true", TRUE));
    WordToken.put("and", new WordToken("and", AND));
    WordToken.put("null", new WordToken("null", NULL));
  }

  /**
   * Construtor
   * 
   * @param input
   * @throws IOException
   */
  public ELLexer(InputStream input) throws IOException {
    super(input);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NumberToken readNumber(int c) throws IOException, ParserException {
    double value = 0.;
    int dot = 10;
    if (!this.isNumber(c)) {
      throw new LexicalException();
    }
    value = c - '0';
    c = this.next();
    while (this.isNumber(c)) {
      value = 10 * value + (c - '0');
      c = this.next();
    }
    if (c == '.') {
      c = this.next();
      while (this.isNumber(c)) {
        value += (double) (c - '0') / dot;
        dot *= 10;
        c = this.next();
      }
    }
    return new NumberToken(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StringToken readString(int c) throws IOException, ParserException {
    StringBuilder sb = new StringBuilder();
    if (!this.isString(c)) {
      throw new LexicalException();
    }
    c = this.next();
    while (!this.isString(c)) {
      if (c == '\\') {
        switch (this.next()) {
          case 'n': {
            sb.append('\n');
            break;
          }
          case 'r': {
            sb.append('\r');
            break;
          }
          case 't': {
            sb.append('\t');
            break;
          }
          case 'f': {
            sb.append('\f');
            break;
          }
          case 'b': {
            sb.append('\b');
            break;
          }
          case '\\': {
            sb.append('\\');
            break;
          }
          default: {
            throw new LexicalException();
          }
        }
      }
      else {
        sb.append((char) c);
      }
      c = this.next();
    }
    this.next();
    return new StringToken(sb.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WordToken readWord(int c) throws IOException, ParserException {
    StringBuilder sb = new StringBuilder();
    if (this.isWordStart(c)) {
      sb.append((char) c);
    }
    c = this.next();
    while (this.isWordPart(c)) {
      sb.append((char) c);
      c = this.next();
    }
    String value = sb.toString();
    WordToken word = WordToken.build(value);
    if (word != null) {
      return word;
    }
    return new IdentifyToken(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Token readSymbol(int c) throws IOException {
    switch (c) {
      case '!': {
        c = this.next();
        if (c == '=') {
          this.next();
          return NOT_EQUAL_TOKEN;
        }
        else {
          return new Token('!');
        }
      }
      case '=': {
        c = this.next();
        if (c == '=') {
          this.next();
          return EQ_TOKEN;
        }
        else {
          return new Token('=');
        }
      }
      case '-': {
        c = this.next();
        if (c == '-') {
          this.next();
          return DEC_TOKEN;
        }
        else {
          return new Token('-');
        }
      }
      case '+': {
        c = this.next();
        if (c == '+') {
          this.next();
          return INC_TOKEN;
        }
        else {
          return new Token('+');
        }
      }
      case '>': {
        c = this.next();
        if (c == '=') {
          this.next();
          return GE_TOKEN;
        }
        else {
          return new Token('>');
        }
      }
      case '<': {
        c = this.next();
        if (c == '=') {
          this.next();
          return LE_TOKEN;
        }
        else {
          return new Token('<');
        }
      }
      default: {
        this.next();
        return new Token(c);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isNumber(int c) {
    return c >= '0' && c <= '9';
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isString(int c) {
    return c == '\"';
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWordStart(int c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'
      || c == '$';
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWordPart(int c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
      || (c >= '0' && c <= '9') || c == '_' || c == '$';
  }

}
