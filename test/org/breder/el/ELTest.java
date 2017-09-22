package org.breder.el;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.breder.parser.exception.ParserException;
import org.junit.Test;

public class ELTest {

  @Test
  public void testPrimitive() throws ParserException {
    Assert.assertEquals(1d, ex("1"));
    Assert.assertEquals(2d, ex("1+1"));
    Assert.assertEquals(1d, ex("2-1"));
    Assert.assertEquals(6d, ex("2*3"));
    Assert.assertEquals(5d, ex("10/2"));
    Assert.assertEquals(false, ex("1>2"));
    Assert.assertEquals(false, ex("1>=2"));
    Assert.assertEquals(true, ex("1<2"));
    Assert.assertEquals(true, ex("1<=2"));
    Assert.assertEquals(1d, ex("true?1:2"));
    Assert.assertEquals(2d, ex("false?1:2"));
    Assert.assertEquals(-1d, ex("-1"));
    Assert.assertEquals(false, ex("!true"));
    Assert.assertEquals("abc", ex("\"abc\""));
    Assert.assertEquals(true, ex("true"));
    Assert.assertEquals(false, ex("false"));
    Assert.assertEquals(true, ex("true or true"));
    Assert.assertEquals(true, ex("true or false"));
    Assert.assertEquals(true, ex("false or true"));
    Assert.assertEquals(false, ex("false or false"));
    Assert.assertEquals(true, ex("true and true"));
    Assert.assertEquals(false, ex("true and false"));
    Assert.assertEquals(false, ex("false and true"));
    Assert.assertEquals(false, ex("false and false"));
    Assert.assertEquals(true, ex("1==1"));
    Assert.assertEquals(false, ex("1!=1"));
    Assert.assertEquals(true, ex("\"a\"==\"a\""));
    Assert.assertEquals(true, ex("1!=null"));
    Assert.assertEquals(true, ex("p!=null", "p", new Person(1, "a")));
    Assert.assertEquals(true, ex("p.name!=null", "p", new Person(1, "a")));
    Assert.assertEquals(true, ex("p.id!=null", "p", new Person(1, "a")));
    Assert.assertEquals(true, ex("p.name==\"a\"", "p", new Person(1, "a")));
    Assert.assertEquals(true, ex("p.id==1", "p", new Person(1, "a")));
    Assert.assertEquals(false, ex("p.id==2", "p", new Person(1, "a")));
    Assert.assertEquals(true, ex("p.father.name==\"b\"", "p", new Person(1,
      "a", new Person(2, "b"))));
    Assert.assertEquals("a", ex("p.name", "p", new Person(1, "a")));
    Assert.assertEquals(new Person(2, "b"), ex("p.father", "p", new Person(1,
      "a", new Person(2, "b"))));
  }

  private Object ex(String code, Object... objects) throws ParserException {
    Map<String, Object> map = new HashMap<String, Object>();
    for (int n = 0; n < objects.length / 2; n++) {
      map.put((String) objects[n * 2], objects[n * 2 + 1]);
    }
    return EL.read(code).execute(map);
  }

  private static class Person {

    private int id;

    private String name;

    private Person father;

    public Person(int id, String name) {
      super();
      this.id = id;
      this.name = name;
    }

    public Person(int id, String name, Person father) {
      super();
      this.id = id;
      this.name = name;
      this.father = father;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Person other = (Person) obj;
      if (father == null) {
        if (other.father != null) {
          return false;
        }
      }
      else if (!father.equals(other.father)) {
        return false;
      }
      if (id != other.id) {
        return false;
      }
      if (name == null) {
        if (other.name != null) {
          return false;
        }
      }
      else if (!name.equals(other.name)) {
        return false;
      }
      return true;
    }
  }

}
