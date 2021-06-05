package ru.stqa.pft.sandbox;

public class Point {

  double x;
  double y;

  public Point (double p1,double p2) {
    this.x = p1;
    this.y = p2;
  }

  public double distance(Point pB) {
    return Math.sqrt((Math.pow((pB.x-this.x),2) + Math.pow((pB.y-this.y),2)));
  }

}
