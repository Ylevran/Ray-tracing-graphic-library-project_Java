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

    //Nx & Ny represent the number of pixels that exist in one slot
    int Nx = 3;
    int Ny = 3;

    float screenWidth = 3;
    float screenHeight = 3;
    float screenDistance =1;


    /******  Help function to throw rays through the all pixels ******/

    private int findIntersectionsOnGeo(int Nx, int Ny, Geometry geo, Camera camera, double screenDistance, double screenWidth, double screenHeight) {
        intersectionsCount = 0;
        for (int i = 0; i < screenWidth; ++i)
            for (int j = 0; j < screenHeight; ++j) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, screenDistance, screenWidth, screenHeight);
                List<Point3D> result = geo.findIntersections(ray);
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
        assertEquals("Sphere of radius 1", 2,
                findIntersectionsOnGeo(Nx,Ny,sphere,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        //TC02: The radius of Sphere is 2.5 (18 points)
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        assertEquals("Sphere of radius 2.5", 18,
                findIntersectionsOnGeo(Nx,Ny,sphere,camera02,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        // TC03: The radius of Sphere is 2 (10 points)
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        assertEquals("Sphere of radius 2", 10,
                findIntersectionsOnGeo(Nx,Ny,sphere,camera02,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        // TC04: The radius of Sphere is 4 (9 points)
        sphere = new Sphere(4, new Point3D(0, 0, 2));
        assertEquals("Sphere of radius 4", 9,
                findIntersectionsOnGeo(Nx,Ny,sphere,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        // TC05: The Sphere is before the view Plane, radius of 0.5 (0 points)
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        assertEquals("Sphere of radius 4", 0,
                findIntersectionsOnGeo(Nx,Ny,sphere,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }


    /**
     * Ray Through Pixel with plane test (3 X 3)
     *
     * {@link elements.Camera #constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithPlane() {


        //TC01: Plane is parallel to view Plane (9 points)
        Plane plane = new Plane(new Point3D(0, 0, 3), new Vector(0, 0, 1));
        assertEquals("Plane parallel", 9,findIntersectionsOnGeo(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        //TC02: Plane is not parallel to view Plane - 01  (9 points)
        plane = new Plane(new Point3D(0.5,-1.5,2),new Point3D(0,0,2.5),new Point3D(0,1.5,3));
        assertEquals("Plane not parallel - 01", 9,findIntersectionsOnGeo(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        //TC03: Plane is not parallel to view Plane - 02 (6 points)
        plane = new Plane(new Point3D(0.5, -2, 2), new Point3D(0, 0, 4), new Point3D(0, 2, 6));
        assertEquals("Plane not parallel - 02", 6,findIntersectionsOnGeo(Nx,Ny,plane,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }

    /**
     * Ray Through Pixel with triangle test (3 X 3)
     *
     * {@link elements.Camera #constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    @Test
    public void constructRayThroughPixelWithTriangle() {

        //TC01: One intersection point
        Triangle triangle = new Triangle(new Point3D(0,-1,2),
                new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("One intersection", 1,findIntersectionsOnGeo(Nx,Ny,triangle,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);


        //TC02: (Two intersection point)
        triangle = new Triangle(new Point3D(0, -20, 2),
                new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        assertEquals("Two intersections", 2,findIntersectionsOnGeo(Nx,Ny,triangle,camera01,screenDistance,screenWidth,screenHeight));
        System.out.println("count: " + intersectionsCount);

    }

}