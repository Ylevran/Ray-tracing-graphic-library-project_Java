package unittests;

import elements.Camera;
import geometries.*;
import org.junit.Test;
import primitives.*;

import javax.swing.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Integration Test Unit for Camera elements and geometries intersections
 *
 * @author Yossef Levran & Shmuel Segal
 */
public class CameraIntegrationTests {


    static int intersectionsCount = 0;

    /******  Help function to throw rays through the all pixels ******/


    private int findIntersectionsOnSphere(int Nx, int Ny, Sphere sphere, Camera camera, double screenDistance, double screenWidth, double screenHeight) {

        intersectionsCount = 0;
        for (int i = 0; i < Nx; ++i)
            for (int j = 0; j < Ny; ++j) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, screenDistance, screenWidth, screenHeight);
                List<Point3D> result = sphere.findIntersections(ray);
                if (result != null)
                    intersectionsCount += result.size();
            }
        return intersectionsCount;
    }

    private int findIntersectionsOnPlane(int Nx, int Ny, Plane plane, Camera camera, double screenDistance, double screenWidth, double screenHeight) {

        intersectionsCount = 0;
        for (int i = 0; i < Nx; ++i)
            for (int j = 0; j < Ny; ++j) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, screenDistance, screenWidth, screenHeight);
                List<Point3D> result = plane.findIntersections(ray);
                if (result != null)
                    intersectionsCount += result.size();
            }
        return intersectionsCount;
    }


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

        assertEquals("Sphere of radius 1", 2, findIntersectionsOnSphere(Nx,Ny,sph,cam,1,3,3));
        System.out.println("count: " + intersectionsCount);


        //TC02: The radius of Sphere is 2.5 (18 points)
        sph = new Sphere(2.5, new Point3D(0, 0, 2.5));
        cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

        assertEquals("Sphere of radius 2.5", 18, findIntersectionsOnSphere(Nx,Ny,sph,cam,1,3,3));
        System.out.println("count: " + intersectionsCount);


        // TC03: The radius of Sphere is 2 (10 points)
        sph = new Sphere(2, new Point3D(0, 0, 2));

        assertEquals("Sphere of radius 2", 10, findIntersectionsOnSphere(Nx,Ny,sph,cam,1,3,3));
        System.out.println("count: " + intersectionsCount);


        // TC04: The radius of Sphere is 4 (9 points)
        sph = new Sphere(2, new Point3D(0, 0, 2));


        assertEquals("Sphere of radius 2", 10,  findIntersectionsOnSphere(Nx,Ny,sph,cam,1,3,3));
        System.out.println("count: " + intersectionsCount);

    }


}