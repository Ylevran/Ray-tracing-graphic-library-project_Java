package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices ) {
            result += p.toString();
        }
        return result;
    }

    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(new Point3D[]{p1, p2, p3});
    }
}