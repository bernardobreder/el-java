package org.breder.el.node;

import java.util.Map;

/**
 * Menor que
 * 
 * 
 * @author Bernardo Breder
 */
public class LowerEqualNode extends ConditionNode {

  /**
   * Construtor
   * 
   * @param left
   * @param right
   */
  public LowerEqualNode(ELNode left, ELNode right) {
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
      return Double.valueOf(left.toString()).compareTo(
        Double.valueOf(right.toString())) <= 0;
    }
    if (left instanceof Comparable<?> && right instanceof Comparable<?>) {
      @SuppressWarnings("unchecked")
      Comparable<Object> lc = (Comparable<Object>) left;
      return lc.compareTo(right) <= 0;
    }
    return false;
  }

}
