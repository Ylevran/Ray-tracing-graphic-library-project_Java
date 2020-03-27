package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class is the basic class representing a triangle in a
 *  3D system
 */
public class Cylinder extends Tube {

    double _height;

    /**
     * Cylinder Constructor receiving radius, axis and height
     * @param _radius
     * @param _axisRay
     * @param _height
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return super.getNormal(p);
    }

    /**
     * height getter
     * @return _height
     */
    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay.toString() +
                ", _radius=" + _radius +
                '}';
    }
}
