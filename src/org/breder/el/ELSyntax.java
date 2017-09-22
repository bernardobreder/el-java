package org.breder.el;

import java.io.IOException;

import org.breder.el.node.AndNode;
import org.breder.el.node.BooleanNode;
import org.breder.el.node.DivNode;
import org.breder.el.node.ELNode;
import org.breder.el.node.EqualNode;
import org.breder.el.node.FieldNode;
import org.breder.el.node.GreaterEqualNode;
import org.breder.el.node.GreaterNode;
import org.breder.el.node.IdentifyNode;
import org.breder.el.node.LowerEqualNode;
import org.breder.el.node.LowerNode;
import org.breder.el.node.MulNode;
import org.breder.el.node.NegNode;
import org.breder.el.node.NotEqualNode;
import org.breder.el.node.NotNode;
import org.breder.el.node.NullNode;
import org.breder.el.node.NumberNode;
import org.breder.el.node.OrNode;
import org.breder.el.node.StringNode;
import org.breder.el.node.SubNode;
import org.breder.el.node.SumNode;
import org.breder.el.node.TernaryNode;
import org.breder.parser.AbstractLexer;
import org.breder.parser.AbstractParser;
import org.breder.parser.exception.ParserException;
import org.breder.parser.exception.SyntaxException;
import org.breder.parser.token.IdentifyToken;
import org.breder.parser.token.NumberToken;
import org.breder.parser.token.StringToken;
import org.breder.parser.token.WordToken;

/**
 * Classe responsável por fazer um parser em cima de uma stream
 * 
 * @author bernardobreder
 * 
 */
public class ELSyntax extends AbstractParser {

  /**
   * Construtor
   * 
   * @param lexer
   */
  public ELSyntax(AbstractLexer lexer) {
    super(lexer);
  }

  /**
   * Realiza uma leitura de um valor de qualquer tipo
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readValue() throws IOException, ParserException {
    return this.readTernary();
  }

  /**
   * Realiza uma leitura de um valor de um valor ternário se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readTernary() throws IOException, ParserException {
    ELNode left = this.readOr();
    while (this.match('?')) {
      this.next();
      ELNode center = this.readOr();
      this.read(':');
      ELNode right = this.readOr();
      left = new TernaryNode(left, center, right);
    }
    return left;
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readOr() throws IOException, ParserException {
    ELNode left = this.readAnd();
    while (this.match(ELLexer.OR)) {
      this.next();
      ELNode right = this.readAnd();
      left = new OrNode(left, right);
    }
    return left;
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readAnd() throws IOException, ParserException {
    ELNode left = this.readCompare();
    while (this.match(ELLexer.AND)) {
      this.next();
      ELNode right = this.readCompare();
      left = new AndNode(left, right);
    }
    return left;
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readCompare() throws IOException, ParserException {
    ELNode left = this.readSumSub();
    for (;;) {
      switch (this.lookTag()) {
        case ELLexer.EQ: {
          this.next();
          ELNode right = this.readSumSub();
          left = new EqualNode(left, right);
          break;
        }
        case ELLexer.NE: {
          this.next();
          ELNode right = this.readSumSub();
          left = new NotEqualNode(left, right);
          break;
        }
        case ELLexer.GE: {
          this.next();
          ELNode right = this.readSumSub();
          left = new GreaterEqualNode(left, right);
          break;
        }
        case ELLexer.LE: {
          this.next();
          ELNode right = this.readSumSub();
          left = new LowerEqualNode(left, right);
          break;
        }
        case '>': {
          this.next();
          ELNode right = this.readSumSub();
          left = new GreaterNode(left, right);
          break;
        }
        case '<': {
          this.next();
          ELNode right = this.readSumSub();
          left = new LowerNode(left, right);
          break;
        }
        default: {
          return left;
        }
      }
    }
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readSumSub() throws IOException, ParserException {
    ELNode left = this.readMulDiv();
    for (;;) {
      switch (this.lookTag()) {
        case '+': {
          this.next();
          ELNode right = this.readMulDiv();
          left = new SumNode(left, right);
          break;
        }
        case '-': {
          this.next();
          ELNode right = this.readMulDiv();
          left = new SubNode(left, right);
          break;
        }
        default: {
          return left;
        }
      }
    }
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readMulDiv() throws IOException, ParserException {
    ELNode left = this.readUnary();
    for (;;) {
      switch (this.lookTag()) {
        case '*': {
          this.next();
          ELNode right = this.readUnary();
          left = new MulNode(left, right);
          break;
        }
        case '/': {
          this.next();
          ELNode right = this.readUnary();
          left = new DivNode(left, right);
          break;
        }
        default: {
          return left;
        }
      }
    }
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readUnary() throws IOException, ParserException {
    switch (this.lookTag()) {
      case '-': {
        this.next();
        ELNode left = this.readNumber();
        return new NegNode(left);
      }
      case '!': {
        this.next();
        ELNode left = this.readLiteral();
        return new NotNode(left);
      }
      default: {
        return this.readLiteral();
      }
    }
  }

  /**
   * Realiza uma leitura de um valor de um valor lógico se possível
   * 
   * @return leitura de um valor
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readLiteral() throws IOException, ParserException {
    switch (this.lookTag()) {
      case ELLexer.NULL:
        return this.readNull();
      case WordToken.NUM:
        return this.readNumber();
      case WordToken.STR:
        return this.readString();
      case ELLexer.TRUE:
        return this.readTrue();
      case ELLexer.FALSE:
        return this.readFalse();
      case WordToken.ID:
        return this.readId();
      default:
        throw new SyntaxException("expected: <expression>");
    }
  }

  /**
   * Realiza uma leitura do valor lógico falso
   * 
   * @return leitura de uma associação
   * @throws IOException
   * @throws ParserException
   */
  public BooleanNode readFalse() throws IOException, ParserException {
    this.next();
    return BooleanNode.build(false);
  }

  /**
   * Realiza uma leitura do valor lógico verdadeiro
   * 
   * @return leitura de uma associação
   * @throws IOException
   * @throws ParserException
   */
  public BooleanNode readTrue() throws IOException, ParserException {
    this.next();
    return BooleanNode.build(true);
  }

  /**
   * Realiza uma leitura de uma associação se possível
   * 
   * @return leitura de uma associação
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readId() throws IOException, ParserException {
    ELNode left = this.readLeftValue();
    while (this.lookTag() == '.') {
      this.next();
      IdentifyToken token = (IdentifyToken) this.read(WordToken.ID);
      left = new FieldNode(left, token.lexeme);
    }
    return left;
  }

  /**
   * Realiza uma leitura de um left value
   * 
   * @return node
   * @throws IOException
   * @throws ParserException
   */
  public ELNode readLeftValue() throws IOException, ParserException {
    return this.readIdentify();
  }

  /**
   * Realiza a leitura de um número
   * 
   * @return leitura de um número
   * @throws IOException
   * @throws ParserException
   */
  public NullNode readNull() throws IOException, ParserException {
    this.next();
    return NullNode.build();
  }

  /**
   * Realiza a leitura de um número
   * 
   * @return leitura de um número
   * @throws IOException
   * @throws ParserException
   */
  public NumberNode readNumber() throws IOException, ParserException {
    NumberToken token = (NumberToken) this.look();
    this.next();
    return new NumberNode(token.value);
  }

  /**
   * Realiza a leitura de uma String
   * 
   * @return leitura de uma String
   * @throws IOException
   * @throws ParserException
   */
  public StringNode readString() throws IOException, ParserException {
    StringToken token = (StringToken) this.look();
    this.next();
    return new StringNode(token.value);
  }

  /**
   * Realiza uma leitura de um identificador
   * 
   * @return leitura de um identificador
   * @throws IOException
   * @throws ParserException
   */
  public IdentifyNode readIdentify() throws IOException, ParserException {
    IdentifyToken token = (IdentifyToken) this.read(WordToken.ID);
    IdentifyNode node = new IdentifyNode(token.lexeme);
    return node;
  }

}
