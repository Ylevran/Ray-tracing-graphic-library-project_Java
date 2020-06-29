package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries is the class representing collection of geometries according to composite pattern
 *
 * @author Yossef
 * @author Shmuel
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries = new LinkedList<>();

    public Geometries(Intersectable... _geometries) {
        add(_geometries);
    }


    public void Geometries(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
    }



    // ***************** Operations ******************** //

    /**
     * Adds a new shape to the list
     * @param geometries
     *               - The shape to add (One of the realists of Geometry)
     */
    public void add(Intersectable... geometries) {
       _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * Remove a shape from the list
     * @param geometries
     *                 - The shape to remove (One of the realists of Geometry)
     */
    public void remove(Intersectable... geometries) {
        for (Intersectable geo : _geometries) {
            _geometries.remove(geo);
        }
    }

    /**
     * @param ray Ray -  the ray that intersect the geometries
     * @return list of Point3D that intersect the Collection
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (_geometries.isEmpty()) return null;
        List<GeoPoint> intersections = null;

        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

}
