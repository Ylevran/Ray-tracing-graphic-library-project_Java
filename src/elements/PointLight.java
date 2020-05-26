package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * Class to manage a point lighting extends from Light and implement LightSource
 *
 *
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    //***************** Constructors **********************//

    /**
     * Contractor to build a Point light
     *
     * @param colorIntensity
     * @param position
     *             - Position of the light
     * @param kC
     *             - Coefficient of Quadratic attenuation of the light in the distance
     * @param kL
     *             - Coefficient of linear weakening of the light in the distance
     * @param kQ
     *             - Coefficient of exponential weakening of the light at a distance
     */
    public PointLight(Color colorIntensity, Point3D position, double kC, double kL, double kQ) {
        super(colorIntensity);
        this._position = new Point3D(position);
        this._kC = kC;
        this._kL = kL;
        this._kQ = kQ;
    }


    // ***************** Getters/Setters ********************** //

    /**
     * dummy overriding Light getIntensity()
     * @return
     */
    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    /**
     *
     * @param p the lighted point
     * @return
     */
    @Override
    public Color getIntensity(Point3D p){
        double dSquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        return (_intensity.reduce(_kC + _kL * d + _kQ * dSquared));
    }

    /**
     *
     * @param p the lighted point
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }

}
