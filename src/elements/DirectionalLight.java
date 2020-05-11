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
     * @param colorIntensity intensity of the light
     * @param direction      direction vector
     */
    public DirectionalLight(Color colorIntensity, Vector direction) {
        super(colorIntensity);
        _direction = direction.normalized();
    }


    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
