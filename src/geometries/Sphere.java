package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.*;
import java.util.Objects;


/**
 * Sphere class is the basic class representing a sphere in a
 *  3D system
 */
public class Sphere extends RadialGeometry {

    Point3D _center;

    /**
     * Sphere Constructor receiving radius and center
     * @param _radius
     * @param _center
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center= new Point3D(
                _center.get_x(),
                _center.get_y(),
                _center.get_z());
    }

    /**
     * center Getter
     * @return center
     */
    public Point3D get_center() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D p){
        Vector normal = p.subtract(_center);
        return normal.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center.toString() +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getPOO();
        Vector v = ray.getDirection();
        Vector u;

        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(ray.getTargetPoint(_radius));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(ray.getTargetPoint(t1), ray.getTargetPoint(t2)); //P1 , P2
        if (t1 > 0)
            return List.of(ray.getTargetPoint(t1));
        else
            return List.of(ray.getTargetPoint(t2));
    }
}