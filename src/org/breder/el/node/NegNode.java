package org.breder.el.node;

import java.util.Map;

/**
 * Negação
 * 
 * 
 * @author Bernardo Breder
 */
public class NegNode extends UnaryNode {

  /**
   * Construtor
   * 
   * @param left
   */
  public NegNode(ELNode left) {
    super(left);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    if (left instanceof Double) {
      return ((Double) left) * -1;
    }
    return left;
  }

}
