package org.breder.el.node;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Nó de Identificador
 * 
 * 
 * @author Bernardo Breder
 */
public class FieldNode extends ELNode {

  /** Left */
  private ELNode left;
  /** Token */
  private String token;

  /**
   * Construtor padrão
   * 
   * @param left
   * @param token nome do identificador
   */
  public FieldNode(ELNode left, String token) {
    this.left = left;
    this.token = token;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(Map<String, Object> map) {
    Object leftValue = left.execute(map);
    {
      try {
        String name =
          "get" + Character.toUpperCase(token.charAt(0)) + token.substring(1);
        return leftValue.getClass().getMethod(name).invoke(leftValue);
      }
      catch (Exception e) {
      }
    }
    {
      try {
        String name =
          "is" + Character.toUpperCase(token.charAt(0)) + token.substring(1);
        return leftValue.getClass().getMethod(name).invoke(leftValue);
      }
      catch (Exception e) {
      }
    }
    {
      try {
        Method method = leftValue.getClass().getMethod(token);
        method.setAccessible(true);
        return method.invoke(leftValue);
      }
      catch (Exception e) {
      }
    }
    {
      Class<? extends Object> c = leftValue.getClass();
      while (c != null) {
        try {
          Field field = c.getDeclaredField(token);
          field.setAccessible(true);
          try {
            return field.get(leftValue);
          }
          catch (Exception e1) {
            break;
          }
        }
        catch (NoSuchFieldException e1) {
        }
        c = c.getSuperclass();
      }
    }
    return null;
  }
}
