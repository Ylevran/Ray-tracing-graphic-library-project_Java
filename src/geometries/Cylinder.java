package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * Cylinder class is the basic class representing a triangle in a
 *  3D system
 */
public class Cylinder extends Tube {

    private double _height;


    // ***************** Constructors ********************** //

    /**
     * Cylinder Constructor receiving radius, axis and height
     * @param radius
     * @param axisRay
     * @param height
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this._height = height;
    }

    /**
     * Cylinder Constructor receiving radius, axis and height and color
     * @param radius
     * @param axisRay
     * @param height
     * @param emissionLight
     */
    public Cylinder(Color emissionLight, double radius, Ray axisRay, double height) {
        this(radius, axisRay, height);
        setEmission(emissionLight);
    }


    // ***************** Getters/Setters ********************** //


    /**
     * @return
     */
    public double getHeight(){
        return _height;
    }



    // ***************** Operations ******************** //

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = super.findIntersections(ray);  // to return the intersection points
        List<GeoPoint> result = new LinkedList<>();
        if (intersections != null) {
            for (GeoPoint geoPoint : intersections) {
                result.add(new GeoPoint(this, geoPoint.getPoint()));
            }
            return result;
        }
        return null;
    }

    /**
     * @param point point to calculate the normal
     * @return normal
     * @author Dan Zilberstein
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D o = _axisRay.getPoint();
        Vector v = _axisRay.getDirection();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
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
