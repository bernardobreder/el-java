package org.breder.el.node;

import java.util.Map;

/**
 * Somador
 * 
 * 
 * @author Bernardo Breder
 */
public class SumNode extends ArithmeticNode {

  /**
   * Construtor
   * 
   * @param left
   * @param right
   */
  public SumNode(ELNode left, ELNode right) {
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
      return ((Double) left) + ((Double) right);
    }
    else {
      return left.toString() + right.toString();
    }
  }

}
