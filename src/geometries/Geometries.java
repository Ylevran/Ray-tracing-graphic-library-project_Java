package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
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


    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (_geometries.isEmpty())
            return null;

        List<Point3D> intersections = null;

        for (Intersectable geo : _geometries) {
            List<Point3D> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

}
