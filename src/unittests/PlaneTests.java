package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Planes
 *
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
                new Vector(3, 9, 1).normalize(),
                new Plane(
                        new Point3D(-1, 1, 2),
                        new Point3D(-4, 2, 2),
                        new Point3D(-2, 1, 5)).getNormal());
    }


    @Test
    public void testFindIntersections() {

        Plane plane = new Plane(new Point3D(0, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));


        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray's neither orthogonal nor parallel to the plane and intersects the plane (1 points)
        assertEquals("Ray's line out of plane", 1, plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1))).size());

        //TC02: Ray's neither orthogonal nor parallel to the plane and not intersects the plane (0 points)
      //  assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1))));


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the plane

        // TC11: Ray included in the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, -1, 0))));

        // TC12: Ray are not included in the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, -1, 0))));









    }


}