package unittests.geometries;

import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Testing Spheres
 *
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
        assertEquals("Wrong sphere normal", new Vector(0, 0, 1), sp.getNormal(new Point3D(0, 0, 2)));
    }

    /**
     * Test method for
     * {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> exp = List.of(p1, p2);
        List<Point3D> points = new LinkedList<>();
        List<Point3D> listOfPoints = List.of(new Point3D(1, 1, 0));

        Vector V1 = new Vector(1, 1, 0);

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Intersectable.GeoPoint> result02 = sphere.findIntersections(new Ray((new Point3D(-1, 0, 0)),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result02.size());

        points.clear();
        for (Intersectable.GeoPoint geo : result02) {
            points.add(geo._point);
        }
        if (points.get(0).get_x().get() > points.get(1).get_x().get()) {
            points = List.of(points.get(1), points.get(0));
        }
        assertEquals("Ray crosses sphere", exp, points);

        // TC03: Ray starts inside the sphere (1 point)
        List<Intersectable.GeoPoint> result03 = sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0),
                new Vector(3, 1, 0)));
        points.clear();
        for (Intersectable.GeoPoint geo : result03) {
            points.add(geo._point);
        }

        assertEquals("Ray from inside sphere", List.of(p2), points);

        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray's line starts after the sphere",
                sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(3, 1, 0))));


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        List<Intersectable.GeoPoint> result11 = sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0)));
        points.clear();
        for (Intersectable.GeoPoint geo : result11) {
            points.add(geo._point);
        }
        assertEquals("Ray from sphere inside", List.of(new Point3D(2, 0, 0)), points);

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray from sphere outside",
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 1, 0))));


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        List<Intersectable.GeoPoint> result13 = sphere.findIntersections(new Ray(new Point3D(1, -2, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points", 2, result13.size());

        points.clear();
        for (Intersectable.GeoPoint geo : result13) {
            points.add(geo._point);
        }

        assertEquals("Line through O, ray crosses sphere",
                List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)), points);

        // TC14: Ray starts at sphere and goes inside (1 points)
        List<Intersectable.GeoPoint> result14 = sphere.findIntersections(new Ray(new Point3D(1, -1, 0),
                new Vector(0, 1, 0)));
        points.clear();
        for (Intersectable.GeoPoint geo : result14) {
            points.add(geo._point);
        }

        assertEquals("Line through O, ray from and crosses sphere", listOfPoints, points);

        // TC15: Ray starts inside (1 points)
        List<Intersectable.GeoPoint> result15 = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0),
                new Vector(0, 1, 0)));
        points.clear();
        for (Intersectable.GeoPoint geo : result15) {
            points.add(geo._point);
        }
        assertEquals("Line through O, ray from inside sphere", listOfPoints, points);

        // TC16: Ray starts at the center (1 points)
        List<Intersectable.GeoPoint> result16 = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(0, 1, 0)));
        points.clear();
        for (Intersectable.GeoPoint geo : result16) {
            points.add(geo._point);
        }
        assertEquals("Line through O, ray from O", listOfPoints, points);

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("Line through O, ray from sphere outside",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertNull("Line through O, ray outside sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0))));


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("Tangent line, ray before sphere",
                sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))));

        // TC20: Ray starts at the tangent point
        assertNull("Tangent line, ray at sphere",
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))));

        // TC21: Ray starts after the tangent point
        assertNull("Tangent line, ray after sphere",
                sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))));


        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("Ray orthogonal to ray head -> O line",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1))));
    }

}