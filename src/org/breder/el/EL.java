package org.breder.el;

import java.io.IOException;
import java.io.InputStream;

import org.breder.el.node.ELNode;
import org.breder.el.node.StringNode;
import org.breder.parser.exception.ParserException;

/**
 * Language
 * 
 * 
 * @author Bernardo Breder
 */
public class EL {

  /**
   * Calcula uma expressão
   * 
   * @param code
   * @return valor da expressão
   * @throws ParserException
   */
  public static ELNode read(String code) throws ParserException {
    StringInputStream input = new StringInputStream(code);
    ELNode left = new StringNode("");
    try {
      ELLexer lexer = new ELLexer(input);
      ELSyntax syntax = new ELSyntax(lexer);
      left = syntax.readValue();
    }
    catch (IOException e) {
    }
    return left;
  }

  private static class StringInputStream extends InputStream {

    /** Conteudo */
    private String content;
    /** Posição */
    private int index;

    /**
     * @param content
     */
    public StringInputStream(String content) {
      this.content = content;
      this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
      if (this.index >= this.content.length()) {
        return -1;
      }
      return this.content.charAt(this.index++);
    }

  }

}
