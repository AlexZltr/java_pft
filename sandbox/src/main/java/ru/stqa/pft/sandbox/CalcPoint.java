package ru.stqa.pft.sandbox;

public class CalcPoint {

  public static void main(String[] args) {

    Point pointA = new Point(9.2, 4.003);
    Point pointB = new Point(5.32, 2);

//    задание п.3
//    System.out.println("Расстояние между pointA и pointB = " + distance(pointA,pointB));

//    задание п.4
    System.out.println("Расстояние между pointA и pointB = " + pointA.distance(pointB));

  }

//   задание п.2
//  public static double distance(Point p1, Point p2) {
//    return Math.sqrt((Math.pow((p2.x-p1.x),2) + Math.pow((p2.y-p1.y),2)));
//  }

}
