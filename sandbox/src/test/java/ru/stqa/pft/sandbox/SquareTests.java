package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;

public class SquareTests {

  @Test
  public void testArea(){
    Square s = new Square(5);
    Assert.assertEquals(s.area(),25);
  }

  @Test
  public void testAreaMinus(){
    Square s = new Square(-2);
    Assert.assertNotEquals(s.area(),-4.0);
    Assert.assertEquals(s.area(),4);
  }

  @Test
  public void testAreaNotNull(){
    Square s = new Square(0);
    Assert.assertNotNull(s.area());
  }






}
