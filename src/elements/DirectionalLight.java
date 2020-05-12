package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;


    // ***************** Constructors ********************** //

    /**
     * Initialize directional light with it's intensity and direction,
     * direction vector will be normalize.
     *
     * @param _colorIntensity intensity of the light
     * @param _direction      direction vector
     */
    public DirectionalLight(Color _colorIntensity, Vector _direction) {
        _intensity = _colorIntensity;
        this._direction = new Vector(_direction.normalized());
    }

    // ***************** Getters/Setters ********************** //

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
