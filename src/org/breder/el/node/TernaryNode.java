package org.breder.el.node;

import java.util.Map;

/**
 * Somador
 * 
 * 
 * @author Bernardo Breder
 */
public class TernaryNode extends BinaryNode {

  /** Center */
  private ELNode center;

  /**
   * Construtor
   * 
   * @param left
   * @param center
   * @param right
   */
  public TernaryNode(ELNode left, ELNode center, ELNode right) {
    super(left, right);
    this.center = center;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    Object right = this.getRight().execute(map);
    Object center = this.center.execute(map);
    return (Boolean) left ? center : right;
  }

}
