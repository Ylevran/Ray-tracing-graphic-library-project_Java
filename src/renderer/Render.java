package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import primitives.Color;
import scene.Scene;

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
        int nX = _imageWriter.getNX();
    }

    public void printGrid(int i, Color yellow) {
        //TODO implementation
    }

    public void writeToImage() {
        //TODO implementation
    }

    private java.awt.Color calcColor(Point3D point) {
        return _scene.getAmbientLight().getIntensity();
    }
}
