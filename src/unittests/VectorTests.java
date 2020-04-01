package unittests;

import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;
import static java.lang.System.out;
import static primitives.Util.isZero;

/**
 * Testing Vectors
 * @author Yossef and Shmuel
 */
public class VectorTests {

    /**
     * Test method for
     * {@link primitives.Vector#Vector(double, double, double)}
     * {@link primitives.Vector#Vector(Vector)}
     * {@link primitives.Vector#Vector(Point3D)}
     * {@link primitives.Vector#Vector(Point3D, Point3D)}
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructor 1 test
        try{
            new Vector(1,1,1);
        } catch (IllegalArgumentException e){
            fail("Failed constructing Vector");
        }

        // TC02: constructor 2 test
        try{
            new Vector(new Point3D(1,1,1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing Vector");
        }

        // TC03: constructor 3 test
        try{
            new Vector(new Point3D(1,1,1), new Point3D(2,2,2));
        } catch (IllegalArgumentException e){
            fail("Failed constructing Vector");
        }

        // TC04: constructor 4 test
        try{
            Vector v1 = new Vector(1,1,1);
            Vector v2 = new Vector(v1);
        } catch (IllegalArgumentException e){
            fail("Failed constructing Vector");
        }


        // =============== Boundary Values Tests ==================
        // TC05: zero vector test
        try {
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for
     * {@link Vector#add(Vector)}
     */
    @Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals("Wrong vector add", new Vector(1, 1, 1),
            new Vector(2, 3, 4).add(new Vector(-1, -2, -3)));
    }

    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals("Wrong vector subtract", new Vector(1, 1, 1),
          new Vector(new Point3D(2, 3, 4)).subtract(new Vector(new Point3D(1, 2, 3))));

    }

    @Test
    public void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals("Wrong vector scale", new Vector(1,1,1).scale(2), new Vector(2,2,2));
    }

    /**
     * Test method for
     * {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    public void dotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, -3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals("dotProduct() wrong value",-28d, v1.dotProduct(v2), 0.00001);


        // =============== Boundary Values Tests ==================
        // TC02: orthogonal test
        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", !isZero(v1.dotProduct(v3)));
    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        // TC01: result length
        if (!isZero(vr.length() - v1.length() * v3.length()))
            fail("ERROR: crossProduct() wrong result length");
        // TC02: orthogonal to its operands
        if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
            fail("ERROR: crossProduct() result is not orthogonal to its operands");


        // =============== Boundary Values Tests ==================
        //TC03: test zero vector
        try {
            v1.crossProduct(v2);
            fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for
     * {@link Vector#normalize()}
     */
    @Test
    public void normalization() {

        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();

        //TC01: normalize() returns a new vector
        if (vCopy != vCopyNormalize)
            fail("ERROR: normalize() function creates a new vector");
        //TC02: normalize() returns an unit vector
        if (!isZero(vCopyNormalize.length() - 1))
            fail("ERROR: normalize() result is not an unit vector");
        //TC03: normalized() does not create a new vector
        Vector u = v.normalized();
        if (u == v)
           fail("ERROR: normalized() function does not create a new vector");
    }
}