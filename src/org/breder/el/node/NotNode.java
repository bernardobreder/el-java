package org.breder.el.node;

import java.util.Map;

/**
 * Not
 * 
 * 
 * @author Bernardo Breder
 */
public class NotNode extends UnaryNode {

  /**
   * Construtor
   * 
   * @param left
   */
  public NotNode(ELNode left) {
    super(left);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object left = this.getLeft().execute(map);
    if (left instanceof Boolean) {
      return !((Boolean) left);
    }
    return left;
  }

}
