package unittests;

import elements.Camera;
import geometries.*;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Integration Test Unit for Camera elements and geometries intersections
 *
 * @author Yossef Levran & Shmuel Segal
 */
public class CameraIntegrationTests {

    Vector vector01 = new Vector(0,0,1);
    Vector vector02 = new Vector(0,-1,0);

    Camera camera01 = new Camera(Point3D.ZERO,vector01,vector02);
    Camera camera02 = new Camera(new Point3D(Coordinate.ZERO,Coordinate.ZERO,new Coordinate(-0.5)),vector01,vector02);

    static int intersectionsCount = 0;

    int Nx = 3;
    int Ny = 3;

    float screenWidth = 3;
    float screenHeight = 3;
    float screenDistance =1;


    /******  Help functions to throw rays through the all pixels ******/

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

    private int findIntersectionsOnTriangle(int Nx, int Ny, Triangle triangle, Camera camera, double screenDistance, double screenWidth, double screenHeight) {

        intersectionsCount = 0;
        for (int i = 0; i < Nx; ++i)
            for (int j = 0; j < Ny; ++j) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, screenDistance, screenWidth, screenHeight);
                List<Point3D> result = triangle.findIntersections(ray);
                if (result != null)
                    intersectionsCount += result.size();
            }
        return intersectionsCount;
    }


    /**
     * Ray Through Pixel with sphere test (3 X 3)
     *
     * {@link elements.Camera #constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithSphere() {


        //TC01: The radius of Sphere is 1 (2 points)
        Sphere sphere = new Sphere(1, new Point3D(0, 0, 3));
        assertEquals("Sphere of radius 1", 2, findIntersectionsOnSphere(Nx,Ny,sphere,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        //TC02: The radius of Sphere is 2.5 (18 points)
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        assertEquals("Sphere of radius 2.5", 18, findIntersectionsOnSphere(Nx,Ny,sphere,camera02,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        // TC03: The radius of Sphere is 2 (10 points)
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        assertEquals("Sphere of radius 2", 10, findIntersectionsOnSphere(Nx,Ny,sphere,camera02,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        // TC04: The radius of Sphere is 4 (9 points)
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        assertEquals("Sphere of radius 2", 10,  findIntersectionsOnSphere(Nx,Ny,sphere,camera02,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }


    /**
     * Ray Through Pixel with plane test (3 X 3)
     *
     * {@link elements.Camera #constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithPlane() {


        //TC01: (9 points)
        Plane plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 0, 1));
        assertEquals("Incorrect amount of intersections", 9,findIntersectionsOnPlane(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

        //TC02: (9 points)
        plane = new Plane(new Point3D(0.5,-1.5,2),new Point3D(0,0,2.5),new Point3D(0,1.5,3));
        assertEquals("Incorrect amount of intersections", 9,findIntersectionsOnPlane(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

        //TC03: (6 points)
        plane = new Plane(new Point3D(0.5, -2, 2), new Point3D(0, 0, 4), new Point3D(0, 2, 6));
        assertEquals("Incorrect amount of intersections", 6,findIntersectionsOnPlane(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }

    /**
     * Ray Through Pixel with triangle test (3 X 3)
     *
     * {@link elements.Camera #constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithTriangle() {

        //TC01: (One intersection point)
        Triangle triangle = new Triangle(new Point3D(0,-1,2),
                new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("Incorrect amount of intersections", 1,findIntersectionsOnTriangle(Nx,Ny,triangle,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

        //TC02: (Two intersection point)
        triangle = new Triangle(new Point3D(0, -20, 2),
                new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        assertEquals("Incorrect amount of intersections", 2,findIntersectionsOnTriangle(Nx,Ny,triangle,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }

}