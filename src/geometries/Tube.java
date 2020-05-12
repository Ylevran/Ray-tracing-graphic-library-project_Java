package geometries;

import primitives.*;

import java.util.Collections;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class is the basic class representing a tube in a
 * 3D system
 */
public class Tube extends RadialGeometry {

    protected final Ray _axisRay;


    // ***************** Constructors ********************** //


    /**
     * Tube Constructor receiving radius and axis Ray
     *
     * @param _radius
     * @param _axisRay
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = _axisRay;
    }


    public Tube(Color emissionLight, double _radius, Ray _axisRay) {
        this(_radius, _axisRay);
        setEmission(emissionLight);
    }

    public Tube(Color emissionLight,Material _material, double _radius, Ray _axisRay) {
        this(emissionLight,_radius,_axisRay);
        this._material = _material;

    }


    // ***************** Getters/Setters ********************** //

    @Override
    public Vector getNormal(Point3D p) {
        //The vector from the point of the cylinder to the given point
        Point3D o = _axisRay.getPoint(); // at this point o = p0
        Vector v = _axisRay.getDirection();

        Vector vector1 = p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if (!isZero(projection)) {
            // projection of P-O on the ray:
            o = o.add(v.scale(projection));
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
    }

    /**
     * axisRay Getter
     *
     * @return axisRay
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    // ***************** Administration  ******************** //

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay.toString() +
                ", _radius=" + _radius +
                '}';
    }

    // ***************** Operations ******************** //

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

      /*  //Given ray (A + ta)
        Point3D pointA = new Point3D(ray.getPoint());
        Vector vectorA = new Vector(ray.getDirection());

        //Tube ray (B + tb)
        Point3D pointB = _axisRay.getPoint();
        Vector vectorB = _axisRay.getDirection();

        double v1 = vectorA.dotProduct(vectorB);
        //if is parallel to tube
        if (Util.isOne(Math.abs(vectorA.dotProduct(vectorB)))) {
            return Collections.emptyList();
        }

        double bb = 1; // it is a unit vector therefore it's squared size is 1
        double aa = 1;
        double bc, ac;
        try {
            //Vector AB
            Vector c = pointB.subtract(pointA);
            //dot-product calc
            bc = vectorB.dotProduct(c);
            ac = vectorA.dotProduct(c);

            //The closest point on
            double t1 = (-(vectorA.dotProduct(vectorB)) * bc + ac * 1) / ( 1 - ab * ab);
            try {
                d = pointA.add(vectorA.scale(t1));
            } catch (Exception ex) {
                d = pointA;
            }

            //The closest point on (B + t2b)
            double t2 = (ab * ac - bc * aa) / (*//*aa * bb*//* 1 - ab * ab);

            try {
                e = pointB.add(vectorB.scale(t2));
            } catch (Exception ex) {
                e = pointB;
            }

            //distance between two rays
            dis = d.distance(e);

        } catch (Exception ex) {
            //If A and B are the same
            d = ray.getPoint3D();
            dis = 0;
        }*/


        return null;
    }
}
