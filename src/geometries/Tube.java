package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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
        //The vector from the point of the cylinder to the given point
        Point3D o = _axisRay.getPOO(); // at this point o = p0
        Vector v = _axisRay.getDirection();

        Vector vector1 = p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if(!isZero(projection))
        {
            // projection of P-O on the ray:
            o = o.add(v.scale(projection));
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
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
