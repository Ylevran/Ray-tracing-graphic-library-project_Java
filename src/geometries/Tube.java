package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class is the basic class representing a tube in a
 *  3D system
 */
public class Tube extends RadialGeometry{

    public Ray _axisRay;

    /**
     * Tube Constructor receiving radius and axis Ray
     * @param _radius
     * @param _axisRay
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = _axisRay;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay.toString() +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * axisRay Getter
     * @return axisRay
     */
    public Ray get_axisRay() {
        return _axisRay;
    }
}
