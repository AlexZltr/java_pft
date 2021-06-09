package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    Point a = new Point(2,4);
    Point b = new Point(4,8);
    System.out.println(a.distance(b));
    Assert.assertEquals(a.distance(b),4.47213595499958);
  }

  @Test
  public void testPointZero() {
    Point a = new Point(0,0);
    Point b = new Point(0,0);
    System.out.println(a.distance(b));
    Assert.assertEquals(a.distance(b),0);
  }

  @Test
  public void testPointMinusValue() {
    Point a = new Point(-1, -5);
    Point b = new Point(2, -5);
    System.out.println(a.distance(b));
    Assert.assertEquals(a.distance(b), 3);
  }

  @Test
  public void testPointSameValue() {
    Point a = new Point(3.5,3.5);
    Point b = new Point(3.5,3.5);
    System.out.println(a.distance(b));
    Assert.assertEquals(a.distance(b),0);

  }
}
