package org.breder.el.node;

import java.util.Map;

/**
 * Literal Boolean
 * 
 * 
 * @author Bernardo Breder
 */
public class NullNode extends PrimitiveNode {

  /** Instancia */
  private static final NullNode INSTANCE = new NullNode();

  /**
   * Constroi um objeto
   * 
   * @return objeto boolean
   */
  public static NullNode build() {
    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    return null;
  }

}
