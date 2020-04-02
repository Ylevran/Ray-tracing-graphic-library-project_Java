package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Planes
 * @author Yossef and Shmuel
 */
public class PlaneTests {

    /**
     * Test method for
     * {@link Plane#getNormal()}
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals("Wrong plane normal",
                new Vector(3,9,1).normalize(),
                new Plane(
                        new Point3D(-1,1,2),
                        new Point3D(-4,2,2),
                        new Point3D(-2,1,5)).getNormal());
    }
}