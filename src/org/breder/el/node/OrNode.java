package org.breder.el.node;

import java.util.Map;

/**
 * Somador
 * 
 * 
 * @author Bernardo Breder
 */
public class OrNode extends ArithmeticNode {

  /**
   * Construtor
   * 
   * @param left
   * @param right
   */
  public OrNode(ELNode left, ELNode right) {
    super(left, right);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    Object right = this.getRight().execute(map);
    return (Boolean) left || (Boolean) right;
  }

}
