package edu.uprm.ciic.data.structures.vector.test;

import edu.uprm.ciic.data.structures.vector.Vector2D;

public class MyTestVector2D {
    public static void main(String[] args) {
        Vector2D V1 = new Vector2D(9, 3);
        Vector2D V2 = new Vector2D(4.1, 1.0);
        System.out.println("V1" + V1);
        System.out.println("V2" + V2);
        System.out.println("Magnitude V1 : " + V1.Magnitude());
        System.out.println("Sum : " + V1.sum(V2));
        System.out.println("Dot product : " + V1.dotProduct(V2));
    }

}
