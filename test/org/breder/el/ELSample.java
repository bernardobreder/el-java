package org.breder.el;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.breder.parser.exception.ParserException;
import org.junit.Assert;

/**
 * Sample para a documentação
 * 
 * @author Tecgraf
 */
public class ELSample {

  /**
   * Testador
   * 
   * @param args
   * @throws ParserException
   */
  public static void main(String[] args) throws ParserException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("a", 1);
    map.put("b", Arrays.asList(1, 2, 3));
    Assert.assertEquals(true, EL.read("1 == 1").execute(null));
    Assert.assertEquals(false, EL.read("1 == 1 and false").execute(null));
    Assert.assertEquals(1, EL.read("a").execute(map));
    Assert.assertEquals(true, EL.read("a == 1").execute(map));
    Assert.assertEquals(3, EL.read("b.size").execute(map));
  }

}
