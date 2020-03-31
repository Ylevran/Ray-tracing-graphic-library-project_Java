package unittests;

import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;
import static java.lang.System.out;
import static primitives.Util.isZero;

public class VectorTests {

    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, -3, -2);

        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", !isZero(v1.dotProduct(v3)));

       // if (!isZero(v1.dotProduct(v3)))
         //   out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        //if (!isZero(v1.dotProduct(v2) + 28))
          //  out.println("ERROR: dotProduct() wrong value");
    }

    @Test
    public void crossProduct() {
    }

    @Test
    public void lengthSquared() {
    }

    @Test
    public void length() {
    }

    @Test
    public void normalize() {
    }

    @Test
    public void normalized() {
    }
}