package unittests.elements;


import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import primitives.Color;
import renderer.*;
import scene.Scene;

import java.awt.*;
import java.util.List;

/**
 * Test rendering abasic image
 *
 * @author Dan
 */
public class LightsTests {


    /**
     * Produce a picture of a many geometries lighted by a directional light
     */
    @Test
    public void coronaSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 0, -200),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.1));


        scene.addGeometries(new Plane(
                new Color(java.awt.Color.black),
                new Material(0,1,150,0.2,0.8),
                new Point3D(0,0,110),
                new Vector(0,-0.724,0.6896)
        ));

        scene.addGeometries(new Plane(
                new Color(29,25,37),
                new Material(0,1,150,0.2,0.8),
                new Point3D(0,0,110),
                new Vector(-0.0453,-0.417,-0.907)
        ));



        scene.addGeometries(
                new Tube(
                        new Color(new Color(221, 160, 221)),
                        new Material(0.3, 0.3, 250), 2,
                        new Ray(new Point3D(0, 0, 5),
                                new Vector(2, 2, 2))));


        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.RED),
                        new Material(0, 0, 0, 0, 1),
                        new Point3D(23, 0, 1),
                        new Point3D(23, 30, 1),
                        new Point3D(10, 0, 1)));

        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.red),
                        new Material(0, 0, 0, 0, 0.5),
                        new Point3D(-23, 0, 1),
                        new Point3D(-23, 30, 1),
                        new Point3D(-10, 0, 1)));


        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(-15, 0, 3)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.pink),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(15, 0, 3)));


        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(-15, 0, 3)));



        //********************************** Spheres around The big sphere ************************************//
        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(24.89, 43.36, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.YELLOW),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(48.39, 12.57, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(39.83, 30.23, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.BLUE),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(49.28,-8.44,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(42.12,-26.94,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(27.94,-41.47,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.YELLOW),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(7.91,-49.37,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.blue),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-13.67,-48.1,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-31.16,-39.11,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.yellow),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-43.4,-24.83,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-49.48,-7.18,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.BLUE),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-48.69,11.37,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.RED),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-41.58,27.77,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.yellow),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-28.85,40.83,50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-12.06, 48.52, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.blue),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(6.72,49.55,50)));


        //**********************************  The big sphere ************************************//

        scene.addGeometries(
                new Sphere(
                        new Color(java.awt.Color.BLUE),
                        new Material(0.5, 0.5, 100,0.6,0.4), 50,
                        new Point3D(0, 0, 50)));



        scene.addLights(new DirectionalLight(new Color(255, 191, 191), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("sphereHDirectional", 200, 200, 1500, 1500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 0, -1000),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50),
                new Vector(1, -1, 2), 1, 0.00001, 0.00000001));

        ImageWriter imageWriter = new ImageWriter("sphereSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

//    /**
//     * Produce a picture of a sphere lighted by a spot lighted by a improved spot
//     */
//    @Test
//    public void advancedSphereSpot() {
//        Scene scene = new Scene("Test scene");
//        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
//        scene.setDistance(1000);
//        scene.setBackground(Color.BLACK);
//        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
//
//        scene.addGeometries(
//                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));
//
//        scene.addLights(new SpotLight.AdvancedSpotLight(
//                new Color(500, 300, 0),
//                new Point3D(-50, 50, -50),
//                new Vector(1, -1, 2), 1, 0.00001, 0.00000001,10));
//
//        ImageWriter imageWriter = new ImageWriter("advancedSphereSpot", 150, 150, 500, 500);
//        Render render = new Render(imageWriter, scene);
//
//        render.renderImage();
//        render.writeToImage();
//    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 0, -1000),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.BLACK),
                        new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150)),
                new Triangle(
                        new Color(java.awt.Color.BLACK),
                        new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150)));

        scene.addLights(new DirectionalLight(
                new Color(300, 150, 150),
                new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(
                new Camera(
                        new Point3D(0, 0, -1000),
                        new Vector(0, 0, 1),
                        new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.BLACK),
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150)),
                new Triangle(
                        new Color(java.awt.Color.BLACK),
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150)));

        scene.addLights(
                new PointLight(
                        new Color(500, 250, 250),
                        new Point3D(10, 10, 130),
                        1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(
                new Camera(
                        new Point3D(0, 0, -1000),
                        new Vector(0, 0, 1),
                        new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(
                        Color.BLACK,
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150)),
                new Triangle(
                        Color.BLACK,
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150)));

        scene.addLights(
                new SpotLight(new Color(500, 250, 250),
                        new Point3D(10, 10, 130), new Vector(-2, 2, 1),
                        1, 0.0001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a improved spot
     *//*
    @Test
    public void advancedTrianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(
                new Camera(
                        new Point3D(0, 0, -1000),
                        new Vector(0, 0, 1),
                        new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(
                        Color.BLACK,
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150)),
                new Triangle(
                        Color.BLACK,
                        new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150),
                        new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150)));

        scene.addLights(new SpotLight.AdvancedSpotLight(
                        new Color(500, 250, 250),
                        new Point3D(10, 10, 130),
                        new Vector(-2, 2, 1),
                        1, 0.0001, 0.000005,10));

        ImageWriter imageWriter = new ImageWriter("advancedTrianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }*/
}

