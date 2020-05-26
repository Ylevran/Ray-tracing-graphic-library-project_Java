package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * Plane class is the basic class representing a plane in a
 * 3D system
 */
public class Plane extends Geometry {

    Point3D _p;
    primitives.Vector _normal;


    // ***************** Constructors ********************** //

    /**
     * Plane Constructor receiving three 3D Points
     *
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
     *
     * @param _p      point
     * @param _normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    /**
     * Plane Constructor receiving a point, normal vector and color
     *
     * @param emissionLight
     * @param _p
     * @param _normal
     */
    public Plane(Color emissionLight, Point3D _p, Vector _normal ) {
       this(_p,_normal);
       this._emission = emissionLight;
    }

    /**
     *Plane Constructor receiving a point, normal vector, color and material
     *
     * @param _p
     * @param _normal
     * @param emissionLight
     * @param material
     */
    public Plane(Color emissionLight, Material material, Point3D _p, Vector _normal ) {
        this(emissionLight, _p,_normal);
        this._material= material;
    }



    // ***************** Getters/Setters ********************** //

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(null);
    }


    // ***************** Administration  ******************** //

    @Override
    public String toString() {
        return "Plane{" +
                "_p =" + _p.toString() +
                ", _normal=" + _normal.toString() +
                '}';
    }


    // ***************** Operations ******************** //

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        Vector p0Q;

        try {
            p0Q = _p.subtract(ray.getPoint());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }


        double nv = _normal.dotProduct(ray.getDirection());
        if (isZero(nv)) //if the ray is parallel to the plane - no intersection
            return null;

        double t = alignZero(_normal.dotProduct(p0Q) / nv);

        if (t > 0) {
            return List.of(new GeoPoint(this, ray.getTargetPoint(t)));
        } else {
            return null;
        }
    }
}
