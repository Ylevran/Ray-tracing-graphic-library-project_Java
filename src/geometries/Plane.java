package geometries;

import primitives.Point3D;
import primitives.Vector;

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
}
