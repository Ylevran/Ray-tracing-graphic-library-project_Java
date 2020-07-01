package renderer;

import elements.*;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.*;
import primitives.Color;
import scene.Scene;

import java.lang.Math;

import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * Class for rendering Scene
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    protected static Random random = new Random();

    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    private static final int SOFTSHADOW_RAYS = 80;
    private static final int MAX_SUPERSAMPLING_LEVEL = 3;


    // ***************** Constructors ********************** //

    /**
     * @param _scene
     */
    public Render(Scene _scene) {
        this._scene = _scene;
    }

    /**
     * @param _imageWriter
     * @param _scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
    }

    // ***************** Getters/Setters ********************** //

    /**
     * Getter
     *
     * @return the _scene
     */
    public Scene get_scene() {
        return _scene;
    }



    // ***************** secondary objects ********************** //

    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }




    // ***************** Operations ******************** //

    /**
     *  Throws rays through the all pixels and for each ray - if it's got
     *  intersection points with the shapes of the scene - paints the closest point
     */
    public void renderImage(boolean aAliasing , boolean sShadow, boolean adapAAliasing ) {

        if (aAliasing == true && adapAAliasing == true)
            throw new IllegalArgumentException("Cannot run Anti Aliasing and adaptive Anti Aliasing together");


        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        //Nx and Ny are the number of pixels in the rows and columns of the view plane
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        //width and height are the width and height of the image.
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        double distance = _scene.getDistance();

        double Rx = width / nX; // pixel width
        double Ry = height / nY; // pixel height

        // Pc is the screen center (Pc = P0 + distance*Vto)
        Point3D Pc = camera.getP0().add(camera.getVTo().scale(distance));

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {

                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);

                    // First case: without anti-aliasing
                    if(aAliasing == false && adapAAliasing == false) {
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background : //
                                calcColor(closestPoint, ray).getColor());
                    }

                    // Second case: with anti-aliasing
                    if(aAliasing == true) {
                        List<Ray> rayList = camera.constructBeamThroughPixel(nX, nY, pixel.col, pixel.row, //
                                distance, width, height);
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background : //
                                averageColor(rayList).getColor());
                    }

                    // Third case: with adaptive anti-aliasing
                    if(adapAAliasing == true){

                        Point3D pixelCenter = camera.getPixelCenter(Pc, nX, nY, pixel.col, pixel.row, width, height );

                        // Create 4 corners pixel Rays and find their colors
                        Point3D p1 = pixelCenter.add(camera.getVRight().scale(- Rx / 2));
                        p1 = p1.add(camera.getVUp().scale(Ry / 2));
                        Vector v1 = p1.subtract(camera.getP0());
                        Ray r1 = new Ray(camera.getP0(), v1.normalize());
                        Color c1 = findClosestIntersection(r1) == null ? _scene.getBackground()
                                : calcColorAdvanced(findClosestIntersection(r1),r1);

                        Point3D p2 = p1.add(camera.getVRight().scale(Rx));
                        Vector v2 = p2.subtract(camera.getP0());
                        Ray r2 = new Ray(camera.getP0(), v2.normalize());
                        Color c2 = findClosestIntersection(r2) == null ? _scene.getBackground()
                                : calcColorAdvanced(findClosestIntersection(r2),r2);

                        Point3D p3 = p2.add(camera.getVUp().scale(-Ry));
                        Vector v3 = p3.subtract(camera.getP0());
                        Ray r3 = new Ray(camera.getP0(), v3.normalize());
                        Color c3 = findClosestIntersection(r3) == null ? _scene.getBackground()
                                : calcColorAdvanced(findClosestIntersection(r3),r3);

                        Point3D p4 = p3.add(camera.getVRight().scale(-Rx));
                        Vector v4 = p4.subtract(camera.getP0());
                        Ray r4 = new Ray(camera.getP0(), v4.normalize());
                        Color c4 = findClosestIntersection(r4) == null ? _scene.getBackground()
                                : calcColorAdvanced(findClosestIntersection(r4),r4);

                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background : //
                                areaColor(camera ,pixelCenter, c1, c2, c3, c4, width / nX, height / nY, MAX_SUPERSAMPLING_LEVEL).
                                        reduce(Math.pow((Math.pow(2, MAX_SUPERSAMPLING_LEVEL)), 2)).getColor());

                    }
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");




        // First case: without anti-aliasing
        if (aAliasing == false && adapAAliasing == false) {
            for (int row = 0; row < nY; ++row)
                for (int column = 0; column < nX; ++column) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    _imageWriter.writePixel(column, row, closestPoint == null ? background : calcColor(closestPoint, ray).getColor());

                }
        }

        // Second case: with anti-aliasing
        if(aAliasing == true){


        }
    }


    /**
     *  Prints grid for the background of the image for test
     *
     * @param interval
     *                 - The interval between line to line
     * @param separator
     *
     */
    public void printGrid(int interval, java.awt.Color separator) {

        double columns = this._imageWriter.getNx();
        double rows = this._imageWriter.getNy();

        //writing the lines
        for(int row =0; row<rows;++row)
            for (int column = 0; column<columns ; ++column)
                if(column % interval == 0 || row % interval ==0)
                    _imageWriter.writePixel(column,row,separator);
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }


    /**
     * Calculates reflected color on point according to Phong model.
     * Calls for recursive helping function.
     *
     * @param geopoint
     * @param inRay
     * @return
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }


    /**
     * Helping function for main calcColor
     * Calculates reflected color on point according to Phong model
     *
     * @param gp
     * @return color
     */
    private Color calcColor(GeoPoint gp, Ray inRay, int level, double k)  {

        if (level == 0 || k < MIN_CALC_COLOR_K) {
            return Color.BLACK;
        }

        Color color = gp.getGeometry().getEmissionLight();

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize(); //direction from point of view to point
        Vector n = gp.getGeometry().getNormal(gp.getPoint()); //normal ray to the surface at the point

        Material material = gp.getGeometry().getMaterial();

        int nShininess = material.getNShininess(); //degree of light shining of the material

        double kd = material.getKd(); //degree of diffusion of the material
        double ks = material.getKs(); //degree of light return shining of the material


        if(_scene.getLightSources() != null) {
            for (LightSource lightSource : _scene.getLightSources()) {

                Vector l = lightSource.getL(gp.getPoint()); //the ray of the light
                double nl = alignZero(n.dotProduct(l)); //dot-product n*l
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) { // Check that 𝒔𝒊𝒈𝒏(𝒍∙𝒏) == 𝒔𝒊𝒈𝒏(𝒗∙𝒏) according to Phong reflectance model
                    double ktr = transparency(lightSource, l, n, gp);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(gp.getPoint()).scale(ktr);
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }
                }
            }
        }

        if (level == 1)
            return Color.BLACK;

        double kr = material.getKr(); //degree of material reflection
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(gp.getPoint(), inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null){
                color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
            }
        }

        double kt = material.getKt(); //degree of material transparency
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefractedRay(gp.getPoint(), inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
            }
        }

        return color;
    }

    /**
     *
     * @param val
     * @return
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Diffusive component of light reflection.
     * @param kd
     *          - degree of light return of the material (double)
     * @param nl
     *          -  dot-product n*l
     * @param lightIntensity
     *          - the intensity of the light as it reaches the object (color)
     * @return intensity of diffusive color (color)
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        if (nl < 0) nl = -nl;
        return lightIntensity.scale(nl * kd);
    }

    /**
     * Calculating the specular light
     *
     * @param ks
     *             - degree of light return shining of the material (double)
     * @param l
     *             - the ray of the light
     * @param n
     *             - normal ray to the surface at the point
     * @param nl
     *             - dot-product n*l
     * @param v
     *             - A ray on the observer's side (camera, etc.)
     * @param nShininess
     *             - degree of light shining of the material (int)
     * @param lightIntensity
     *             - light intensity at the point
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {

        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0)
            return Color.BLACK; // view from direction opposite to r vector

        return lightIntensity.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Returns transparency factor on specific point
     *
     * @param ls light source
     * @param l
     * @param n
     * @param geoPoint
     * @return transparency factor
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.getPoint(), lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return 1.0;

        double lightDistance = ls.getDistance(geoPoint.getPoint());
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(geoPoint.getPoint()) - lightDistance) <= 0){
                ktr *= gp.getGeometry().getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
        }

        return ktr;
    }


    /**
     * Returns refracted ray with delta moving
     *
     * @param pointGeo
     * @param inRay
     * @param n
     * @return refracted ray
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n ){
        return new Ray(pointGeo, inRay.getDirection(), n);
    }

    /**
     * Returns reflected Ray with delta moving
     *
     * @param pointGeo
     * @param inRay
     * @param n
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n){
        Vector v = inRay.getDirection();
        double vn = v.dotProduct(n);

        if (vn == 0)
            return null;

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r , n);
    }

    /**
     * Finds intersections of a ray with the scene geometries and get the
     * closest intersection point to the ray head. Returns null if there are
     * no intersection.
     * @param ray intersecting the scene
     * @return closest point
     */
    private GeoPoint findClosestIntersection(Ray ray){
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint gp : intersections){
            double distance = ray_p0.distance(gp.getPoint());

            if (distance < closestDistance){
                closestPoint = gp;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }



    // ********************** Advanced functions for improvements features ***********************

    /**
     * Calculate the average of a color in a pixel
     *
     * @param rayBeam
     * @return
     */
    private Color averageColor(List<Ray> rayBeam){
        Color color = new Color(0,0,0);
        for(Ray ray : rayBeam){
            if (findClosestIntersection(ray) == null)
                color = color.add(_scene.getBackground());
            else
                color = color.add(calcColorAdvanced(findClosestIntersection(ray),ray));
        }

        return color.reduce(rayBeam.size());
    }

    /**
     * Calculates reflected color on point according to Phong model.
     * Calls for recursive helping function.
     *
     * @param geopoint
     * @param inRay
     * @return
     */
    private Color calcColorAdvanced(GeoPoint geopoint, Ray inRay) {
        return calcColorAdvanced(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     * Helping function for main calcColor
     * Calculates reflected color on point according to Phong model
     *
     * @param gp
     * @return color
     */
    private Color calcColorAdvanced(GeoPoint gp, Ray inRay, int level, double k)  {

        if (level == 0 || k < MIN_CALC_COLOR_K) {
            return Color.BLACK;
        }

        Color color = gp.getGeometry().getEmissionLight();

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize(); //direction from point of view to point
        Vector n = gp.getGeometry().getNormal(gp.getPoint()); //normal ray to the surface at the point

        Material material = gp.getGeometry().getMaterial();

        int nShininess = material.getNShininess(); //degree of light shining of the material

        double kd = material.getKd(); //degree of diffusion of the material
        double ks = material.getKs(); //degree of light return shining of the material


        if(_scene.getLightSources() != null) {
            for (LightSource lightSource : _scene.getLightSources()) {

                Vector l = lightSource.getL(gp.getPoint()); //the ray of the light
                double nl = alignZero(n.dotProduct(l)); //dot-product n*l
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) { // Check that 𝒔𝒊𝒈𝒏(𝒍∙𝒏) == 𝒔𝒊𝒈𝒏(𝒗∙𝒏) according to Phong reflectance model
                    double ktr; // shadow calculating
                    if (lightSource instanceof PointLight && ((PointLight) lightSource).getRadius() != 0) { // with soft shadow
                        ktr = transparencyAdvanced((PointLight) lightSource, l, n, gp);
                    }
                    else{
                        ktr = transparency(lightSource, l, n, gp); // without soft shadow
                    }
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(gp.getPoint()).scale(ktr);
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }
                }
            }
        }

        if (level == 1)
            return Color.BLACK;

        double kr = material.getKr(); //degree of material reflection
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(gp.getPoint(), inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null){
                color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
            }
        }

        double kt = material.getKt(); //degree of material transparency
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefractedRay(gp.getPoint(), inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
            }
        }

        return color;
    }

    /**
     * Returns transparency factor on specific point with soft shadow
     * @param ls
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    private double transparencyAdvanced(PointLight ls, Vector l, Vector n, GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.getPoint(), lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return 1.0 * softShadow(ls, geoPoint, l, n);

        double lightDistance = ls.getDistance(geoPoint.getPoint());
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(geoPoint.getPoint()) - lightDistance) <= 0){
                ktr *= gp.getGeometry().getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K)
                    return softShadow(ls, geoPoint, l, n);
            }
        }

        return ktr * softShadow(ls, geoPoint, l, n);

    }

    /**
     * Return transparency coefficient based on beam sampling for soft shadow
     * @param ls
     * @param geoPoint
     * @param l
     * @param n
     * @return
     */
    private double softShadow(PointLight ls, GeoPoint geoPoint, Vector l, Vector n){

        // Find plane orthonormal basis
        Plane plane = new Plane(ls.getPosition(), l);
        List<Vector> basis = plane.findOrthoBasis();

        double softMean = 0;

        for (int i = 0; i < SOFTSHADOW_RAYS; ++i){
            // Generating random point on ls area
            Point3D rand = ls.getPosition();
            double rRand = randomInRange(0, Math.pow(ls.getRadius(), 2));
            double angleRand = randomInRange(0, 2 * Math.PI);
            double yRand = Math.sqrt(rRand) * Math.sin(angleRand);
            double xRand = Math.sqrt(rRand) * Math.cos(angleRand);
            if (!isZero(xRand)) rand = rand.add(basis.get(0).scale(xRand));
            if (!isZero(yRand)) rand = rand.add(basis.get(1).scale(yRand));

            // Check intersection from geopoint
            Ray lightRay = new Ray(geoPoint.getPoint(), new Vector(rand, geoPoint.getPoint()), n);
            List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
            if (intersections == null){
                softMean++;
            }
        }
        softMean = softMean / SOFTSHADOW_RAYS;
        return softMean;
    }

    /**
     * Generate double random number in range
     *
     * @param min value in range
     * @param max value in range
     * @return rand num
     */
    private static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }



    // ********************** Acceleration: performance improvements **************************

    /**
     * Recursive function for adaptive supersampling implementation
     * @param cam - camera
     * @param center center of the given area in the pixel
     * @param c1 color of top left corner of the area
     * @param c2 color of top right corner of the area
     * @param c3 color of bottom right corner of the area
     * @param c4 color of bottom left corner of the area
     * @param Rx area width
     * @param Ry area height
     * @param level depth of recursion
     * @return
     */
    private Color areaColor(Camera cam, Point3D center, Color c1, Color c2, Color c3, Color c4,
                            double Rx, double Ry, int level){

        Color color = new Color(0,0,0);

        // last level condition
        if (level == 0){
            color = color.add(c1).add(c2).add(c3).add(c4);
            color = color.reduce(4);
            return color;
        }

        // Check if 4 corners are similar color
        if (c1.similar(c2) && c2.similar(c3) && c3.similar(c4) && c4.similar(c1) ){
            color = color.add(c1).add(c2).add(c3).add(c4);
            color = color.reduce(4);
            color = color.scale(Math.pow((Math.pow(2, level)), 2)); // color * (2^level)^2
            return color;
        }

        // Find colors of new corners
        Vector v0 = center.subtract(cam.getP0());
        Ray r0 = new Ray(cam.getP0(), v0.normalize());
        Color c0 = findClosestIntersection(r0) == null ? _scene.getBackground()
                : calcColorAdvanced(findClosestIntersection(r0),r0);

        // left new point
        Point3D p1 = center.add(cam.getVRight().scale(- Rx / 2));
        Vector v1 = p1.subtract(cam.getP0());
        Ray r1 = new Ray(cam.getP0(), v1.normalize());
        Color c5 = findClosestIntersection(r1) == null ? _scene.getBackground()
                : calcColorAdvanced(findClosestIntersection(r1),r1);

        // top new point
        Point3D p2 = center.add(cam.getVUp().scale(Ry / 2));
        Vector v2 = p2.subtract(cam.getP0());
        Ray r2 = new Ray(cam.getP0(), v2.normalize());
        Color c6 = findClosestIntersection(r2) == null ? _scene.getBackground()
                : calcColorAdvanced(findClosestIntersection(r2),r2);

        // right new point
        Point3D p3 = center.add(cam.getVRight().scale(Rx / 2));
        Vector v3 = p3.subtract(cam.getP0());
        Ray r3 = new Ray(cam.getP0(), v3.normalize());
        Color c7 = findClosestIntersection(r3) == null ? _scene.getBackground()
                : calcColorAdvanced(findClosestIntersection(r3),r3);

        // bottom new point
        Point3D p4 = center.add(cam.getVUp().scale(- Ry / 2));
        Vector v4 = p4.subtract(cam.getP0());
        Ray r4 = new Ray(cam.getP0(), v4.normalize());
        Color c8 = findClosestIntersection(r4) == null ? _scene.getBackground()
                : calcColorAdvanced(findClosestIntersection(r4),r4);

        // Recursive call for top left area
        Point3D newCenter = center.add(cam.getVRight().scale(- Rx / 4));
        newCenter = newCenter.add(cam.getVUp().scale(Ry / 4));
        color = color.add(areaColor(cam, newCenter, c1, c6, c0, c5,Rx / 2, Ry / 2, level - 1));

        // Recursive call for top right area
        newCenter = center.add(cam.getVRight().scale(Rx / 4));
        newCenter = newCenter.add(cam.getVUp().scale(Ry / 4));
        color = color.add(areaColor(cam, newCenter, c6, c2, c7, c0, Rx / 2, Ry / 2, level - 1));

        // Recursive call for bottom left area
        newCenter = center.add(cam.getVRight().scale(- Rx / 4));
        newCenter = newCenter.add(cam.getVUp().scale(- Ry / 4));
        color = color.add(areaColor(cam, newCenter, c0, c7, c3, c8, Rx / 2, Ry / 2, level - 1));

        // Recursive call for bottom right area
        newCenter = center.add(cam.getVRight().scale(Rx / 4));
        newCenter = newCenter.add(cam.getVUp().scale(- Ry / 4));
        color = color.add(areaColor(cam, newCenter, c5, c0, c8, c4, Rx / 2, Ry / 2, level - 1));

        return color;
    }


    // ********************** Multi-threading **************************
    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }


}