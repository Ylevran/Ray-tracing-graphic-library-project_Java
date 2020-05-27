package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class SpotLight extends PointLight {

    private Vector _direction;
    double _concentration;

    //***************** Constructors **********************//

    /**
     * Constructor of a spot light
     *
     * @param _colorIntensity
     * @param _position       - Position of the light (Point3D)
     * @param _direction      - direction of lighting (Normalized Vector)
     * @param _kC             - Coefficient of Quadratic attenuation of the light in the distance
     * @param _kL             - Coefficient of linear weakening of the light in the distance
     * @param _kQ             - Coefficient of exponential weakening of the light at a distance
     */
    public SpotLight(Color _colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_colorIntensity, _position, _kC, _kL, _kQ);
        this._direction = new Vector(_direction).normalized();
    }


    public SpotLight(Color _colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ, double _concentration) {
        this(_colorIntensity,_position,_direction,_kC,_kL,_kQ);
        this._concentration = _concentration;
    }

    // ***************** Getters/Setters ********************** //


    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));

        if (isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointLightIntensity = super.getIntensity(p);

        if (_concentration != 1) {
            factor = Math.pow(factor, _concentration);
        }

        return (pointLightIntensity.scale(factor));
    }
}
