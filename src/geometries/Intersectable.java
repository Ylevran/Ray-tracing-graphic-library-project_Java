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
     * returns list of intersections between ray and the intersectable
     * @param ray Ray
     * @return list of intersections
     */
    List<GeoPoint> findIntersections(Ray ray);

    /**
     * GeoPoint is just a tuple holding
     * references to a specific point in a specific geometry
     */
    public static class GeoPoint {

        public Geometry _geometry;
        public Point3D _point;

        // ***************** Constructors ********************** //

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this._geometry = geometry;
            this._point = point;
        }

        // ***************** Getters/Setters ********************** //

        /**
         * @return _point
         */
        public Point3D getPoint(){
            return _point;
        }

        /**
         * @return _geometry
         */
        public Geometry getGeometry(){
            return _geometry;
        }




        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(_geometry, geoPoint._geometry) &&
                    Objects.equals(_point, geoPoint._point);
        }


    }





}
