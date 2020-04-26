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
    List<Point3D> findIntersections(Ray ray);
}
