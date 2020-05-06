package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.Color;


/**
 *  Represents a scene
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 *
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Scene {

    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;


    // ***************** Constructors ********************** //

    /**
     * Constructor - initializes the fields with default values
     *
     * @param _sceneName
     *                 - the name of the scene (String)
     */
    public Scene(String _sceneName) {
        this._name = _sceneName;
        this._background = null;
        this._ambientLight = null;
        this._camera = null;
        this._distance = 0.0;
        _geometries = new Geometries(); // Initialize empty list
    }



    //***************** Getters/setters ****************************//

    /**
     * Getter
     *
     * @return the screen Distance
     */
    public double getDistance(){
        return _distance;
    }

    /**
     * Getter
     *
     * @return the _background color
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * Getter
     *
     * @return the shapes Of Scene
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * Getter
     *
     * @return the _camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * Getter
     *
     * @return the _ambientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * Setter
     *
     * @param _name
     *              - the name of the scene (String)
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * Setter for background color
     *
     * @param _background
     *                - Color instance for the _background color
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * Setter
     *
     * @param _ambientLight
     *                  - the _ambientLight to set
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * Setter
     *
     * @param _geometries
     */
    public void setGeometries(Geometries _geometries) {
        this._geometries = _geometries;
    }

    /**
     * Setter
     *
     * @param _camera
     *              - the _camera to set
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * Setter
     *
     * @param _distance
     *              -the _screenDistance to set
     *
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }


    // ***************** Operations ******************** //


    /**
     * Adds geometric shape to the scene
     *
     * @param intersectables
     *                      - The geometric shapes to add
     */
    public void addGeometries(Intersectable... intersectables) {
        for (Intersectable geometry:intersectables ) {
            _geometries.add(geometry);
        }

    }
}
