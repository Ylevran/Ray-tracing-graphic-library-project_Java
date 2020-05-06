package unittests.geometries;

import geometries.*;
import primitives.*;
import org.junit.Test;
import primitives.Point3D;

import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest {

    /**
     * Test Method for
     * {@link Geometries#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(3, 0 ,0));
        Triangle triangle = new Triangle(
                new Point3D(0,1,0), new Point3D(0,4,0), new Point3D(0,3,1.82));
        Plane plane = new Plane(new Point3D(1,0,0), new Point3D(3,4,0), new Point3D(2,2,3));

        // ============ Equivalence Partitions Tests ==============

        //TC01: Objects Collection is empty (0 points)
        Geometries geometries = new Geometries();
        assertEquals("Collection is empty",null,
                geometries.findIntersections(new Ray(new Point3D(5.07,-2.24,0), new Vector(-5.07, 5.24, 0.62))));

        geometries.add(sphere, triangle, plane);

        //TC02: No geometrie is intersected (0 points)
        assertEquals("No geometrie is intersected",null,
                geometries.findIntersections(new Ray(new Point3D(2,-3,0), new Vector(5, 0, 0))));

        //TC03: Only one geometrie is intersected (1 point)
        assertEquals("one geometrie is intersected" ,1,
                (geometries.findIntersections(new Ray(new Point3D(2,-3,0), new Vector(-2, 1, 1.94)))).size());

        //TC04: All the geometries are intersected (4 points)
        assertEquals("All geometries are intersected" ,4,
                (geometries.findIntersections(new Ray(new Point3D(5.07,-2.24,0), new Vector(-5.07, 5.24, 0.62)))).size());


        // =============== Boundary Values Tests ==================

        //TC11: Many (But not All) geometries are intersected (2 points)
        assertEquals( "Not all geometries intersected" ,2,
                (geometries.findIntersections(new Ray(new Point3D(2,-3,0), new Vector(-2, 6, 0.62)))).size());

    }
}