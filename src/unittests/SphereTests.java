package unittests;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Spheres
 * @author Yossef and Shmuel
 */
public class SphereTests {

    /**
     * Test method for
     * {@link Sphere#getNormal(Point3D)}
     */
    @Test
    public void getNormalTest() {
        Sphere sp = new Sphere(1.0, new Point3D(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals("Wrong sphere normal",new Vector(0,0,1),sp.getNormal(new Point3D(0,0,2)));
    }
}