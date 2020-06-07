package renderer;

import elements.Camera;
import elements.LightSource;
import elements.Material;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Color;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * Class for rendering Scene
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

    /**
     * static num for head shadow ray moving
     */
    private static final double DELTA = 0.1;


    // ***************** Constructors ********************** //

    public Render(Scene _scene) {
        this._scene = _scene;
    }

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


    // ***************** Operations ******************** //

    /**
     *  Throws rays through the all pixels and for each ray - if it's got
     *  intersection points with the shapes of the scene - paints the closest point
     */
    public void renderImage() {
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


        for (int row = 0; row < nY; ++row)
            for (int column = 0; column < nX; ++column) {
               Ray ray = camera.constructRayThroughPixel(nX,nY,column,row,distance,width,height);
               List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
               // List<GeoPoint> intersectionPoints = geometries.findIntersections(camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height));

                if(intersectionPoints == null)
                    _imageWriter.writePixel(column,row,background);
                else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column,row,calcColor(closestPoint).getColor());
                }

             /*   if(intersectionPoints != null)
                    _imageWriter.writePixel(column,row,calcColor(getClosestPoint(intersectionPoints)).getColor());
                else
                    _imageWriter.writePixel(column,row,background);*/
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
     * Calculate reflected color on point according to Phong model
     *
     * @param gp
     * @return color
     */
    private Color calcColor(GeoPoint gp)  {

        Color color = _scene.getAmbientLight().getIntensity();
        color = color.add(gp.getGeometry().getEmissionLight());

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize(); //direction from point of view to point
        Vector n = gp.getGeometry().getNormal(gp.getPoint()); //normal ray to the surface at the point

        Material material = gp.getGeometry().getMaterial();

        int nShininess = material.getNShininess(); //degree of light shining of the material

        double kd = material.getKd(); //degree of light return of the material
        double ks = material.getKs(); //degree of light return shining of the material

        if(_scene.getLightSources() != null) {
            for (LightSource lightSource : _scene.getLightSources()) {

                Vector l = lightSource.getL(gp.getPoint()); //the ray of the light
                double nl = alignZero(n.dotProduct(l)); //dot-product n*l
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) { // Check that 𝒔𝒊𝒈𝒏(𝒍∙𝒏) == 𝒔𝒊𝒈𝒏(𝒗∙𝒏) according to Phong reflectance model
                    if (unshaded(lightSource, l, n, gp)) {
                        Color lightIntensity = lightSource.getIntensity(gp.getPoint());
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity)
                        );
                    }
                }
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
        if (nl < 0) {
            nl = -nl;
        }
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
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return lightIntensity.scale(ks * Math.pow(minusVR, p));
    }

    /**
     *  Finds the closest point to the camera from all intersection points
     *
     * @param intersectionPoints
     * @return  - The closest point to the camera
     *
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {

        // initialization
        GeoPoint result = null;
        double minDistance = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);

            if (distance < minDistance) {
                minDistance = distance;
                result = geo;
            }
        }
        return result;

    }

    /**
     * Checks weather the point is shaded or not
     *
     * @param light light source
     * @param l
     * @param n
     * @param geopoint
     * @return shader or unshaded
     */
    private boolean unshaded(LightSource light ,Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geopoint._point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return true;
        }

        double lightDistance = light.getDistance(geopoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0) {
                return false;
            }
        }
        return true;
    }
}
