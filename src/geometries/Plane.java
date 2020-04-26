package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Plane class is the basic class representing a plane in a
 *  3D system
 */
public class Plane implements Geometry {

    Point3D _p;
    primitives.Vector _normal;

    /**
     * Plane Constructor receiving three 3D Points
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p = new Point3D(p1.get_x(), p1.get_y(), p1.get_z());
        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();
        _normal = N;
//        _normal = N.scale(-1);
    }

    /**
     * Plane Constructor receiving a point and normal vector
     * @param _p point
     * @param _normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }
    public Vector getNormal(){
        return getNormal(null);
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_p =" + _p.toString() +
                ", _normal=" + _normal.toString() +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getPOO();
        Vector v = ray.getDirection();

        if (p0 == _p)
            return List.of(p0);

        double nv = _normal.dotProduct(v);
        if (isZero(nv))
            return null;

        double nQMinusP0 = _normal.dotProduct(_p.subtract(p0));
        if (isZero(nQMinusP0))
            return null;

        double t = alignZero(nQMinusP0 / nv);
        if (t > 0)
            return List.of(p0.add(v.scale(t)));

        return null;
    }
}
