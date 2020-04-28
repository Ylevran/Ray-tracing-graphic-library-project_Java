package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
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

    /**
     * Test Method for
     * {@link Plane#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {

        Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 2, 1), new Point3D(2, 0, 1));


        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray's neither orthogonal nor parallel to the plane and intersects the plane (1 points)
        assertEquals("Ray's line out of plane", 1, plane.findIntersections(new Ray(new Point3D(0, -2, 0), new Vector(0, 2, 1))).size());

        //TC02: Ray's neither orthogonal nor parallel to the plane and not intersects the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(-1, -1, 0))));


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the plane

        // TC11: Ray included in the plane (0 points)
        assertEquals("Ray's line is not in the plane", null, plane.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

        // TC12: Ray are not included in the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(2, -1, 0), new Vector(-1, 0, 0))));


        // **** Group: Ray's line is orthogonal to the plane

        //TC13 Ray is  orthogonal to the plane before (0 points)
        assertEquals("the Ray is not orthogonal to the plane", null, plane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, 2, 0))));

        //TC14 Ray is  orthogonal to the plane after (0 points)
        assertEquals("the Ray is not orthogonal to the plane", null, plane.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(0, -2, 0))));

        //TC15 Ray is  orthogonal in the plane
        assertEquals("the Ray is not orthogonal in the plane", null, plane.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 1, 0))));

        //TC16 Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q)
        assertEquals("Ray's not start at Q0", null, plane.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 2, -1))));


    }
}