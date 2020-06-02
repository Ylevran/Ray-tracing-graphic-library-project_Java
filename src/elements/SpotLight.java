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

    protected Vector _direction;
    //double _concentration;

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


    /*public SpotLight(Color _colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ, double _concentration) {
        this(_colorIntensity,_position,_direction,_kC,_kL,_kQ);
        this._concentration = _concentration;
    }*/

    // ***************** Getters/Setters ********************** //


    /**
     * @param p the lighted point
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * @param p the lighted point
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {

        double dSquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        Vector vector;
        if(p.subtract(_position).normalized() == null)
            vector = new Vector(_direction);
        else
            vector = p.subtract(_position).normalized();

        return (_intensity.scale(Math.max(0,_direction.dotProduct(vector)))
                .reduce(_kC + _kL * d + _kQ * dSquared));

    }

    /**
     *
     */
    public static class AdvancedSpotLight extends SpotLight {

        double _concentration;

        public AdvancedSpotLight(Color _colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ, double _concentration) {
            super(_colorIntensity, _position, _direction, _kC, _kL, _kQ );
            this._concentration = _concentration;
        }

        // ***************** Getters/Setters ********************** //


        /**
         * @param p
         * @return
         */
        @Override
        public Color getIntensity(Point3D p) {
            double dSquared = p.distanceSquared(_position);
            double d = p.distance(_position);

            Vector vector;
            if(p.subtract(_position).normalized() == null)
                vector = new Vector(_direction);
            else
                vector = p.subtract(_position).normalized();

            return (_intensity.scale(Math.max(0,Math.pow(_direction.dotProduct(vector),_concentration)))
                    .reduce(_kC + _kL * d + _kQ * dSquared));
        }
    }

}
