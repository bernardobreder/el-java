package org.breder.el.node;

import java.util.Map;

/**
 * Numero
 * 
 * 
 * @author Bernardo Breder
 */
public class NumberNode extends PrimitiveNode {

  /** Valor */
  private double value;

  /**
   * Construtor
   * 
   * @param value
   */
  public NumberNode(double value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    return this.value;
  }

}
