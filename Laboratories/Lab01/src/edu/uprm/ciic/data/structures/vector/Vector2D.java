package edu.uprm.ciic.data.structures.vector;

import java.util.Objects;
import java.util.Vector;

public class Vector2D {
    private static final double EPSILON = 0.000001;
    private double x;
    private double y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {

        return "(" + "x=" + x + ", y=" + y + ")";
    }
    public double Magnitude(){
        //return Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2));
        return Math.sqrt(this.dotProduct(this));
    }
    public Vector2D scale(double c){

        return new Vector2D(c + this.getX(),c + this.getY());
    }
    public Vector2D sum (Vector2D v2){
        //sum between v1 & v2
        return new Vector2D(this.getX() + v2.getX(), this.getY() + v2.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Vector2D)){
            throw new IllegalArgumentException("obj must be a Vector2D type");
        }
        Vector2D temp = (Vector2D) o;
        return compareSmall(this.getX(),temp.getX()) && compareSmall(this.getY(),temp.getY());
    }

    private static boolean compareSmall(double a, double b){
        double temp = Math.abs(a-b);
        return temp < EPSILON;

    }

    public Vector2D substract (Vector2D v2) {
        return new Vector2D(this.getX() - v2.getX(), this.getY() - v2.getY());
    }
    public double dotProduct(Vector2D V2) {
        return this.getX()* V2.getX() + this.getY()* V2.getY();
    }
}
