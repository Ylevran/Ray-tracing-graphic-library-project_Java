package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Geometries implements Intersectable {

    private List<Intersectable> _geometries;

    public Geometries(){
        _geometries = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... _geometries) {
        add( _geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries ) {
            _geometries.add(geo);
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
