package org.breder.el.node;

import java.util.Map;

/**
 * Nao igual
 * 
 * 
 * @author Bernardo Breder
 */
public class NotEqualNode extends ConditionNode {

  /**
   * Construtor
   * 
   * @param left
   * @param right
   */
  public NotEqualNode(ELNode left, ELNode right) {
    super(left, right);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    Object right = this.getRight().execute(map);
    if (left instanceof Number && right instanceof Number) {
      return !Double.valueOf(left.toString()).equals(
        Double.valueOf(right.toString()));
    }
    else if (left == null) {
      return right != null;
    }
    else {
      return !left.equals(right);
    }
  }

}
