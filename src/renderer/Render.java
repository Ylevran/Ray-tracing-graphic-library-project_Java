package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import primitives.Color;
import scene.Scene;

import java.util.List;


/**
 * Class for rendering Scene
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;


    // ***************** Constructors ********************** //

    public Render(Scene _scene) {
        this._scene = _scene;
    }

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
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

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        double distance = _scene.getDistance();

        Ray ray;

        for (int row = 0; row < nX; ++row)
            for (int column = 0; column < nY; ++column) {
                ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if(intersectionPoints == null){
                    _imageWriter.writePixel(column,row,background);
                } else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column,row,calcColor(closestPoint));

                }
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
            for (int column = 0; column<columns ; ++column){
                if(column % interval == 0 || row % interval ==0)
                {
                    _imageWriter.writePixel(column,row,separator);
                }
            }
    }


    public void writeToImage() {
        _imageWriter.writeToImage();
    }


    /**
     * Wrapper function of sending to the _calcColor function
     *
     * @param point
     * @return - The result from the extended calcColor function
     */
    private java.awt.Color calcColor(Point3D point) {
        return _scene.getAmbientLight().getIntensity();
    }


    /**
     *  Finds the closest point to the camera from all intersection points
     *
     * @param intersectionPoints
     * @return  - The closest point to the camera
     *
     */
    private Point3D getClosestPoint(List<Point3D> intersectionPoints) {

        if (intersectionPoints == null) {
            throw new IllegalArgumentException("The list of points cannot be null");
        }

        // initialization
        double minDistance = Double.MAX_VALUE;

        Point3D result = null;
        Point3D P0 = new Point3D(_scene.getCamera().get_p0());

        for(Point3D p:intersectionPoints){
            double distance = P0.distance(p);

            if(P0.distance(p) < minDistance){
                result = p;
                minDistance = distance;
            }
        }
        return result;
    }
}
