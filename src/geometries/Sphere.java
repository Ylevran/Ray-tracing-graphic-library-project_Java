package geometries;

import primitives.Coordinate;
import java.util.Objects;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;



public class Sphere extends RadialGeometry {

    Point3D _center;

    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center= new Point3D(
                _center.get_x(),
                _center.get_y(),
                _center.get_z());
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center.toString() +
                ", _radius=" + _radius +
                '}';
    }
}