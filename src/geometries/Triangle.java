package geometries;

import elements.Material;
import primitives.*;

import java.util.List;

/**
 * Triangle class is the basic class representing a triangle in a
 *  3D system
 */
public class Triangle extends Polygon {


    public Triangle(Color color, Material material, Point3D p1, Point3D p2, Point3D p3) {
        this(color,p1,p2,p3);
    }

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices ) {
            result += p.toString();
        }
        return result;
    }

    // ***************** Constructors ********************** //

    /**
     * Triangle Constructor receiving three points
     * @param p1 first
     * @param p2 second
     * @param p3 third
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(new Point3D[]{p1, p2, p3});
    }

    /**
     * @param color
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Color color, Point3D p1, Point3D p2, Point3D p3) {
        this(p1,p2,p3);
    }
    
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (_plane.findIntersections(ray) == null)
            return null;

        List <Point3D> planeIntersection = _plane.findIntersections(ray);

        Vector v1 = _vertices.get(0).subtract(ray.getPOO());
        Vector v2 = _vertices.get(1).subtract(ray.getPOO());
        Vector v3 = _vertices.get(2).subtract(ray.getPOO());

        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();

        double d1 = Util.alignZero(ray.getDirection().dotProduct(n1));
        double d2 = Util.alignZero(ray.getDirection().dotProduct(n2));
        double d3 = Util.alignZero(ray.getDirection().dotProduct(n3));

        // if the intersection is inside triangle
        if (d1 > 0.0 && d2 > 0.0 && d3 > 0.0)
            return planeIntersection;

        if (d1 < 0.0 && d2 < 0.0 && d3 < 0.0)
            return planeIntersection;

        // else if the intersection is outside triangle
        return null;

    }
}