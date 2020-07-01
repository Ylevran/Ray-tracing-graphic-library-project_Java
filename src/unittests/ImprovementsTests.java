package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import renderer.Render;
import scene.Scene;

import java.io.IOException;

/**
 * Tests for improvements features:
 * anti-aliasing
 * soft-shadow
 * multi-threading
 * adaptive anti-aliasing
 *
 * @author Shmuel & Yossef
 *
 */
public class ImprovementsTests {

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     * features: none
     */
    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7, 10 ));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere and triangle with point light and shade
     * features: anti-aliasing, soft-shadows
     */
    @Test
    public void SphereTriangleInitialAdvanced() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7, 10 ));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitialAdvanced", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).
                setMultithreading(3).
                setDebugPrint();

        render.renderImage(true, true, false);
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     * features: anti-aliasing, soft-shadows, multi-threading, adaptive anti-aliasing
     */
    @Test
    public void SphereTriangleInitialAdvancedAcceleration() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7, 10 ));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitialAdvancedAcceleration", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).
                setMultithreading(3).
                setDebugPrint();

        render.renderImage(false, true, true);
        render.writeToImage();
    }

    /**
     * Produce a complex picture with many geometries and many light sources
     * features: none
     */
    @Test
    public void finalTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 250, -80),
                new Vector(0, -250, 80),
                new Vector(0,-250,80).crossProduct(new Vector(-1,0,0))));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Plane(
                Color.BLACK,
                new Material(0.4,0.6,150,0.2,0.4),
                new Point3D(0,0,50),
                new Vector(0,0,1)));

        //**********************************  Sphere ************************************//

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK),
                        new Material(0, 1, 100, 0.2, 0.8), 20,
                        new Point3D(-50, -20, 30)));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK),
                        new Material(0, 1, 100, 0.2, 0.8), 30,
                        new Point3D(-65, -75, 20)));

        //**********************************  Polygons ************************************//


        scene.addGeometries(new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0.5, 0.5, 150, 0.8, 0.2),
                                new Point3D(0, -40, 0),
                                new Point3D(20, -20, 50),
                                new Point3D(20, -60, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(20, -20, 50),
                                new Point3D(3.52, -36.88, 50),
                                new Point3D(0, -40, 0)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(3.52, -36.88, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-3.65, -36.76, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-3.65, -36.67, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-20.1, -20, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-20.1, -20, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-20, -60, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-20, -60, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-3.78, -43.85, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-3.78, -43.85, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(3.39, -43.51, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(3.39, -43.51, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(20, -60, 50)));


        //**********************************  Cylinder ************************************//

        scene.addGeometries(
                new Cylinder(
                        new Color(java.awt.Color.red),
                        new Material(0.3,0.3,150,0.3,0.3), 25,
                        new Ray(
                                new Point3D(50, -40, 12),
                                new Vector(0,0,1)), 100));


        scene.addLights(new PointLight(
                new Color(1000, 600, 0),
                new Point3D(-100, -100, 34),
                0.0004, 0.0000006, 30));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(41.68, -67.52, 28.84), new Vector(34.29, 9.93, 0), 1,
                0.0004, 0.000006, 40));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006, 30));

        ImageWriter imageWriter = new ImageWriter("finalTest", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }


    /**
     * Produce a complex picture with many geometries and many light sources
     * features: anti-aliasing, soft-shadows, multi-threading, adaptive anti-aliasing
     */
    @Test
    public void finalTestAdvanced() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 250, -80),
                new Vector(0, -250, 80),
                new Vector(0,-250,80).crossProduct(new Vector(-1,0,0))));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));


        scene.addGeometries(new Plane(
                Color.BLACK,
                new Material(0.4,0.6,150,0.2,0.4),
                new Point3D(0,0,50),
                new Vector(0,0,1)));

        //**********************************  Sphere ************************************//

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK),
                        new Material(0, 1, 100, 0.2, 0.8), 20,
                        new Point3D(-50, -20, 30)));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK),
                        new Material(0, 1, 100, 0.2, 0.8), 30,
                        new Point3D(-65, -75, 20)));

        //**********************************  Polygons ************************************//


        scene.addGeometries(new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0.5, 0.5, 150, 0.8, 0.2),
                                new Point3D(0, -40, 0),
                                new Point3D(20, -20, 50),
                                new Point3D(20, -60, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(20, -20, 50),
                                new Point3D(3.52, -36.88, 50),
                                new Point3D(0, -40, 0)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(3.52, -36.88, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-3.65, -36.76, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-3.65, -36.67, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-20.1, -20, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-20.1, -20, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-20, -60, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-20, -60, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(-3.78, -43.85, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(-3.78, -43.85, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(3.39, -43.51, 50)),
                new geometries.Polygon
                        (new Color(java.awt.Color.YELLOW),
                                new Material(0, 1, 150, 0.7, 0.3),
                                new Point3D(3.39, -43.51, 50),
                                new Point3D(0, -40, 0),
                                new Point3D(20, -60, 50)));


        //**********************************  Cylinder ************************************//

        scene.addGeometries(
                new Cylinder(
                        new Color(java.awt.Color.red),
                        new Material(0.3,0.3,150,0.3,0.3), 25,
                        new Ray(
                                new Point3D(50, -40, 12),
                                new Vector(0,0,1)), 100));


        scene.addLights(new PointLight(
                new Color(1000, 600, 0),
                new Point3D(-100, -100, 34),
                0.0004, 0.0000006, 30));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(41.68, -67.52, 28.84), new Vector(34.29, 9.93, 0), 1,
                0.0004, 0.000006, 40));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006, 30));

        ImageWriter imageWriter = new ImageWriter("finalTestAdvanced", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).
                setMultithreading(3).
                setDebugPrint();

        render.renderImage(false, true, true);
        render.writeToImage();
    }
}
