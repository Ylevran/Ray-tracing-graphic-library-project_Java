package geometries;

import primitives.*;

import java.util.List;
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
        return null;
    }
}