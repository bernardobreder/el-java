package org.breder.el.node;

import java.util.Map;

/**
 * Literal Boolean
 * 
 * 
 * @author Bernardo Breder
 */
public class BooleanNode extends PrimitiveNode {

  private static final BooleanNode TRUE = new BooleanNode(true);

  private static final BooleanNode FALSE = new BooleanNode(false);

  private boolean flag;

  /**
   * Constroi um objeto
   * 
   * @param flag
   * @return objeto boolean
   */
  public static BooleanNode build(boolean flag) {
    if (flag) {
      return TRUE;
    }
    else {
      return FALSE;
    }
  }

  /**
   * Construtor
   * 
   * @param flag
   */
  public BooleanNode(boolean flag) {
    this.flag = flag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    return this.flag;
  }

}
