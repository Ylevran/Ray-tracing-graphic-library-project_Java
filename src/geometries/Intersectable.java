package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 * interface Intersectable is the basic interface for all intersectable objects
 * who are implementing findIntersections method.
 */
public interface Intersectable {

    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        // ***************** Constructors ********************** //

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }
    }


    /**
     * returns list of intersections between ray and the intersectable
     * @param ray Ray
     * @return list of intersections
     */
    List<Point3D> findIntersections(Ray ray);
}
