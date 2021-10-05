package edu.uprm.ciic.data.structures.vector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2DTest {
    private Vector2D v1;
    private Vector2D v2;
    private static final double EPSILON= .000001;


    private static boolean compareSmall(double a, double b){
        double temp = Math.abs(a-b);
        return temp < EPSILON;

    }

    @Before
    public void setUp() throws Exception {
        this.v1 = new Vector2D(2,2);
        this.v2 = new Vector2D(1,3);

    }

    @Test
    public void getX() {
        assertTrue("x is not set to 2", this.v1.getX()==2.0);
    }

    @Test
    public void setX() {
        this.v1.setX(0);
        assertTrue("x is not set to 0", this.v1.getX()==0.0);

    }

    @Test
    public void getY() {
        assertTrue("y is not set to 2", this.v1.getY()==2.0);

    }

    @Test
    public void setY() {
        this.v1.setY(3);
        assertTrue("Y is not set to 0", this.v1.getY()==3);
    }

    @Test
    public void magnitude() {
        double mag = this.v1.Magnitude();
        double r = Math.sqrt(8);
        assertTrue("Magnitud is not correct",  compareSmall(mag,r));
    }

    @Test
    public void scale() {
        Vector2D r = this.v1.scale(2);
        Vector2D v3 = new Vector2D(4,4);
        assertTrue("r is not equal to v3" + r ,  r.equals(v3));

    }

    @Test
    public void sum() {
        Vector2D r =this.v1.sum(v2);
        Vector2D v3 = new Vector2D(3,5);
        assertTrue("r is not equal to v3", r.equals(v3));
    }

    @Test
    public void substract() {
        Vector2D r =this.v1.substract(v2);
        Vector2D v3 = new Vector2D(1,- 1);
        assertTrue("r is not equal to v3", r.equals(v3));
    }

    @Test
    public void dotProduct() {
        double val = this.v1.dotProduct(v2);
        double r = 8;
        assertTrue("Dot product is not correct", compareSmall(val,r));
    }
}