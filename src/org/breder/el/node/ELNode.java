package org.breder.el.node;

import java.util.Map;

/**
 * Representa um nó da linguagem
 * 
 * 
 * @author Bernardo Breder
 */
public abstract class ELNode {

  /**
   * Construtor padrão
   */
  public ELNode() {
    super();
  }

  /**
   * Execute um nó
   * 
   * @param map
   * @return valor
   */
  public abstract Object execute(Map<String, Object> map);

  /**
   * Retorna o proprio
   * 
   * @param <E>
   * @return proprio
   */
  @SuppressWarnings("unchecked")
  public <E extends ELNode> E cast() {
    return (E) this;
  }

}
