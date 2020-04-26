package unittests;

import geometries.Polygon;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Triangles
 * @author Yossef and Shmuel
 */
public class TriangleTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3),
                tr.getNormal(new Point3D(0, 0, 1)));
    }


    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point3D(0,1,0),
                new Point3D(1,0,0),
                new Point3D(0.5, 0.5, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside triangle (1 point)
        assertEquals("line is inside triangle", List.of(new Point3D(0.48, 0.52, 0.23)),
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(2.08, 0.52,0.23))));

        // TC02: Ray's line is outside against edge (0 points)
        assertEquals("line is outside against edge" ,null,
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(1.6, 1,0.33))));

        // TC03: Ray's line is outside against vertex (0 points)
        assertEquals("line is outside against vertex" ,null,
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(2.1, 0.5,1.31))));


        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on edge (0 points)
        assertEquals("line is on edge" , null,
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(1.76, 0.84,0.33))));

        //TC12: Ray's line is on vertex (O points)
        assertEquals("line is on vertex",null,
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(2.1, 0.5,1))));

        //TC13: Ray's line is on edge's continuation (0 points)
        assertEquals("line is on edge's continuation",null,
                triangle.findIntersections(new Ray(new Point3D(-1.6, 0, 0), new Vector(1.9, 0.7,1.41))));


    }
}