package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import primitives.Color;
import scene.Scene;

import java.util.List;

public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

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
                    _imageWriter.writePixel(column-1,row-1,calcColor(closestPoint));

                }
            }


    }

    public void printGrid(int interval, java.awt.Color separator) {
        double width = this._imageWriter.getWidth();
        double height = this._imageWriter.getHeight();

        //writing the lines
        for(int row =0; row<height;++row)
            for (int column = interval; column<interval ;column+=interval){
                _imageWriter.writePixel(column,row,separator);
                _imageWriter.writePixel(row,column,separator);
            }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    private java.awt.Color calcColor(Point3D point) {
        return _scene.getAmbientLight().getIntensity();
    }

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
