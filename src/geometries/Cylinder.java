package geometries;

import elements.Material;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * Cylinder class is the basic class representing a cylinder in a
 * 3D system
 */
public class Cylinder extends Tube {

    private double _height;


    // ***************** Constructors ********************** //

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

    public Cylinder(Color _emissionLight, double _radius, Ray _axisRay, double _height) {
        this(_radius,_axisRay,_height);
        this._emission = _emissionLight;
    }

    public Cylinder(Color _emissionLight, Material _material, double _radius, Ray _axisRay, double _height) {
        this(_emissionLight, _radius, _axisRay, _height);
        this._material = _material;
    }


    // ***************** Getters/Setters ********************** //


    /**
     * @return
     */
    public double getHeight(){
        return _height;
    }

    /**
     * @param point point to calculate the normal
     * @return normal
     * @author Dan Zilberstein
     */

    // ***************** Operations ******************** //

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
    public List<GeoPoint> findIntersections(Ray ray) {
       Plane planeTop = new Plane(_axisRay.getTargetPoint(_height), _axisRay.getDirection());
        Plane planeBottom = new Plane(_axisRay.getPoint(), _axisRay.getDirection());
        List<GeoPoint> intersections = null;


        List<GeoPoint> tempIntersection1 = planeBottom.findIntersections(ray);
        if (tempIntersection1 != null) {
            if (alignZero(_radius - _axisRay.getPoint().distance(tempIntersection1.get(0)._point)) > 0) {
                intersections = new LinkedList<GeoPoint>();
                intersections.add(tempIntersection1.get(0));
            }
        }

        List<GeoPoint> tempIntersection2 = planeTop.findIntersections(ray);
        if (tempIntersection2 != null) {
            if (alignZero(_radius - _axisRay.getTargetPoint(_height).distance(tempIntersection2.get(0)._point)) > 0) {
                if (intersections == null)
                    intersections = new LinkedList<GeoPoint>();
                intersections.add(tempIntersection2.get(0));
            }
        }

        List<GeoPoint> tempIntersection3 = super.findIntersections(ray);
        if (tempIntersection3 != null) {
            double maxLenSquare = _height * _height + _radius * _radius;
            for (GeoPoint geoPoint : tempIntersection3) {
                if (alignZero(maxLenSquare - geoPoint._point.distanceSquared(_axisRay.getPoint())) > 0 &&
                        alignZero(maxLenSquare - geoPoint._point.distanceSquared(_axisRay.getTargetPoint(_height))) > 0) {
                    if (intersections == null)
                        intersections = new LinkedList<GeoPoint>();
                    intersections.add(geoPoint);
                }
            }
        }
        if (intersections != null) {
            for (GeoPoint geoPoint : intersections) {
                geoPoint._geometry = this;
            }
        }
        return intersections;
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
