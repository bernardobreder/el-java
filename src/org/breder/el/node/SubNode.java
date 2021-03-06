package org.breder.el.node;

import java.util.Map;

/**
 * Subtração
 * 
 * 
 * @author Bernardo Breder
 */
public class SubNode extends ArithmeticNode {

  /**
   * Construtor
   * 
   * @param left
   * @param right
   */
  public SubNode(ELNode left, ELNode right) {
    super(left, right);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    Object right = this.getRight().execute(map);
    if (left instanceof Double && right instanceof Double) {
      return ((Double) left) - ((Double) right);
    }
    return left;
  }

}
