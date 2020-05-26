package geometries;

import primitives.Material;
import primitives.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Triangle class is the basic class representing a triangle in a
 * 3D system
 */
public class Triangle extends Polygon {

    // ***************** Constructors ********************** //

    public Triangle(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
        setEmission(emissionLight);
    }

    /**
     * Triangle Constructor receiving three points
     *
     * @param p1 first
     * @param p2 second
     * @param p3 third
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * riangle Constructor receiving three points and color
     *
     * @param emissionLight
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        this(p1, p2, p3);
        setEmission(emissionLight);
    }


    // ***************** Operations ******************** //

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (_plane.findIntersections(ray) == null) return null;
        List<GeoPoint> planeIntersections = _plane.findIntersections(ray);


        Point3D p0 = ray.getPoint();
        Vector v = ray.getDirection();

        Vector v1 = _vertices.get(0).subtract(ray.getPoint());
        Vector v2 = _vertices.get(1).subtract(ray.getPoint());
        Vector v3 = _vertices.get(2).subtract(ray.getPoint());


        double d1 = v.dotProduct(v1.crossProduct(v2));
        if (Util.isZero(d1)) return null;
        double d2 = v.dotProduct(v2.crossProduct(v3));
        if (Util.isZero(d2)) return null;
        double d3 = v.dotProduct(v3.crossProduct(v1));
        if (Util.isZero(d3)) return null;

        // if the intersection is inside triangle
        if ((d1 > 0.0 && d2 > 0.0 && d3 > 0.0) || (d1 < 0.0 && d2 < 0.0 && d3 < 0.0)) {
            List<GeoPoint> result = new ArrayList<>();
            for (GeoPoint geo : planeIntersections) {
                result.add(new GeoPoint(this, geo.getPoint()));
            }
            result.get(0)._geometry = this;
            return result;
        }

        return null;
    }

    // ***************** Administration  ******************** //

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices) {
            result += p.toString();
        }
        return result;
    }
}