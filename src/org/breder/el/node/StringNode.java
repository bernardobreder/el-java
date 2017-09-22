package org.breder.el.node;

import java.util.Map;

/**
 * Literal de string
 * 
 * 
 * @author Bernardo Breder
 */
public class StringNode extends PrimitiveNode {

  /** Token */
  private String token;

  /**
   * Construtor
   * 
   * @param token
   */
  public StringNode(String token) {
    this.token = token;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    return this.token;
  }

}
