package org.breder.el.node;

import java.util.Map;

/**
 * Nó de Identificador
 * 
 * 
 * @author Bernardo Breder
 */
public class IdentifyNode extends ELNode {

  /** Token */
  private String token;

  /**
   * Construtor padrão
   * 
   * @param token nome do identificador
   */
  public IdentifyNode(String token) {
    this.token = token;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    return map.get(token);
  }

  /**
   * Retorna
   * 
   * @return token
   */
  public String getToken() {
    return token;
  }

}
