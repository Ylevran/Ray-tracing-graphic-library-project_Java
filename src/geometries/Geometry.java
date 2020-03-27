package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
  */
public interface Geometry {
    /**
     * get Normal Vector
     * @param p point3D
     * @return normal vector
     */
    Vector getNormal(Point3D p);
}
