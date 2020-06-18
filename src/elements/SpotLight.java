package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class SpotLight extends PointLight {
    Vector _direction;
    double _concentration;

    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ,
                     double concentration, double radius) {
        super(colorIntensity, position, kC, kL, kQ, radius);
        this._direction = new Vector(direction).normalized();
        this._concentration = concentration;
    }

    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ, double radius) {
        this(colorIntensity, position, direction, kC, kL, kQ, 1,radius);
    }

    public SpotLight(Color colorIntensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(colorIntensity, position, direction, kC, kL, kQ, 1,0);
    }


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


    /**
     *
     */
/*    public static class AdvancedSpotLight extends SpotLight {

        double _concentration;

        public AdvancedSpotLight(Color _colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ, double _concentration) {
            super(_colorIntensity, _position, _direction, _kC, _kL, _kQ );
            this._concentration = _concentration;
        }

        // ***************** Getters/Setters ********************** //


        *//**
         * @param p
         * @return
         *//*
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
    }*/

}
