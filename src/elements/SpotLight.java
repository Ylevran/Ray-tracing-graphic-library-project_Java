package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * @author Yossef Levran
 * @author Shmuel Segal
 */
public class SpotLight extends PointLight {
    Vector _direction;
    double _concentration;

    //***************** Constructors **********************//

    /**
     * @param colorIntensity
     * @param position
     * @param direction
     * @param kC
     * @param kL
     * @param kQ
     * @param concentration
     * @param radius
     */
    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ,
                     double concentration, double radius) {
        super(colorIntensity, position, kC, kL, kQ, radius);
        this._direction = new Vector(direction).normalized();
        this._concentration = concentration;
    }

    /**
     * @param colorIntensity
     * @param position
     * @param direction
     * @param kC
     * @param kL
     * @param kQ
     * @param radius
     */
    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ, double radius) {
        this(colorIntensity, position, direction, kC, kL, kQ, 1,radius);
    }

    /**
     * @param colorIntensity
     * @param position
     * @param direction
     * @param kC
     * @param kL
     * @param kQ
     */
    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(colorIntensity, position, direction, kC, kL, kQ, 1,0);
    }

    // ***************** Operations ******************** //


    /**
     * @return spotlight intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);

        if (_concentration != 1) {
            factor = Math.pow(factor, _concentration);
        }

        return (pointlightIntensity.scale(factor));
    }

}
