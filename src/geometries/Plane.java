package geometries;

import elements.Material;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
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
        _p = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = new Vector(U.crossProduct(V));

        N.normalize();
        _normal = N;
    }

    /**
     * Plane Constructor receiving a point and normal vector
     *
     * @param _p      point
     * @param _normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    /**
     * Plane Constructor receiving a point, normal vector and color
     * @param _emissionLight
     * @param _p
     * @param _normal
     */
    public Plane(Color _emissionLight, Point3D _p, Vector _normal) {
       this(_p,_normal);
       this._emission = _emissionLight;
    }

    public Plane(Color _emissionLight, Material _material, Point3D _p, Vector _normal) {
        this(_emissionLight,_p,_normal);
        this._material = _material;
    }

    // ***************** Getters/Setters ********************** //

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    /*public Vector getNormal() {
        return getNormal(null);
    }*/


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


    /**
     * Return list of two orthogonals to normal and normalised vectors in the plane
     * @return list
     */
    public List<Vector> findOrthoBasis(){
        List<Vector> basis = new LinkedList<>();
        Vector ortho1 = this._normal.findOrthogonal();
        basis.add(ortho1.normalize());
        Vector ortho2 = this._normal.crossProduct(ortho1);
        basis.add(ortho2.normalize());
        return basis;
    }
}
