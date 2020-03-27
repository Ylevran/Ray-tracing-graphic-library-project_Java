package geometries;

import primitives.Point3D;

/**
 * Triangle class is the basic class representing a triangle in a
 *  3D system
 */
public class Triangle extends Polygon {

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices ) {
            result += p.toString();
        }
        return result;
    }

    /**
     * Triangle Constructor receiving three points
     * @param p1 first
     * @param p2 second
     * @param p3 third
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(new Point3D[]{p1, p2, p3});
    }
}