package unittests;

import elements.Camera;
import geometries.*;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration Test Unit for Camera elements and geometries intersections
 * @author Yossef Levran & Shmuel Segal
 */
public class CameraIntegrationTests {

    /**
     * Ray Through Pixel with Sphere test (3 X 3)
     */
    @Test
    public void constructRayThroughPixelWithSphere() {

        int Nx = 3;
        int Ny = 3;

        //TC01: The radius of Sphere is 1 (2 points)
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sph = new Sphere(1, new Point3D(0, 0, 3));


        int count = 0;
        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                Ray ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("Sphere of radius 1", 2, count);
        System.out.println("count: " + count);


        //TC02: The radius of Sphere is 2.5 (18 points)
        sph = new Sphere(2.5, new Point3D(0, 0, 2.5));
        cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

        count = 0;
        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                Ray ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("Sphere of radius 2.5", 18, count);
        System.out.println("count: " + count);


        // TC03: The radius of Sphere is 2 (10 points)
        sph = new Sphere(2, new Point3D(0, 0, 2));

        count = 0;
        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                Ray ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("Sphere of radius 2", 10, count);
        System.out.println("count: " + count);


        // TC04: The radius of Sphere is 4 (9 points)
        sph = new Sphere(2, new Point3D(0, 0, 2));

        count = 0;
        for (int i = 0; i < Nx; ++i) {
            for (int j = 0; j < Ny; ++j) {
                Ray ray = cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("Sphere of radius 2", 10, count);
        System.out.println("count: " + count);
    }








}