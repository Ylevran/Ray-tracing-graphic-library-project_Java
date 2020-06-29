package geometries;

import elements.Material;
import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class is the basic class representing a tube in a
 * 3D system
 */
public class Tube extends RadialGeometry {

    //TODO Test
    protected final Ray _axisRay;


    // ***************** Constructors ********************** //


    /**
     * Tube Constructor receiving radius and axis Ray
     *
     * @param radius
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this._axisRay = axisRay;
    }

    /**
     * Tube Constructor receiving radius and axis Ray and color
     *
     * @param emissionLight
     * @param radius
     * @param axisRay
     */
    public Tube(Color emissionLight, double radius, Ray axisRay) {
        this(radius, axisRay);
        this._emission = emissionLight;
    }

    public Tube(Color emissionLight, Material _material, double _radius, Ray _axisRay) {
        this(emissionLight,_radius,_axisRay);
        this._material = _material;

    }


    // ***************** Getters/Setters ********************** //

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

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        //Given ray (A + ta)
        Point3D pointA = new Point3D(ray.getPoint());
        Vector vectorA = new Vector(ray.getDirection());

        //Tube ray (B + tb)
        Point3D pointB = _axisRay.getPoint();
        Vector vectorB = _axisRay.getDirection();

        //if  vectorA  and pointA are parallel to tube (zero intersection points)
        if (Util.isOne(Math.abs(vectorA.dotProduct(vectorB))))
            return null;

        if(pointA.equals(pointB))
            pointB =_axisRay.getTargetPoint(-1);

        Vector pointAB = new Vector(pointA.subtract(pointB));

        double v1 = vectorA.dotProduct(vectorB);
        double v2 = pointAB.dotProduct(vectorB);

        double a, b, c;

        Vector vectorC;
        Vector vectorD = null;

        if(isZero(v1))
            vectorC = vectorA;
        else
            vectorC = vectorA.subtract(vectorB.scale(v1));

        if(isZero(v2))
            vectorD = pointAB;
        else if(!pointAB.equals(vectorB.scale(v2)))
            vectorD =pointAB.subtract(vectorB.scale(v2));

        if(isZero(v2) || !pointAB.equals(vectorB.scale(v2))) {
            b = 2 * vectorC.dotProduct(vectorD);
            c = vectorD.dotProduct(vectorD) - _radius * _radius;
        } else {
            b = 0;
            c = -_radius * _radius;
        }

        a = vectorC.dotProduct(vectorC);
        double desc = b*b - 4*a*c;

        //if there is no solution
        if(desc < 0) return  null;

        //if there is one solution
        if(isZero(desc)) return null;

        double direction1 = (-b+Math.sqrt(desc))/(2*a);
        double direction2 = (-b-Math.sqrt(desc))/(2*a);

        if(direction1 <=0 &&  direction2 <=0)
            return null;
        else if(direction1 > 0 && direction2 > 0)
            return List.of(
                    new GeoPoint(this,ray.getTargetPoint(direction1)),
                    new GeoPoint(this,ray.getTargetPoint(direction2)));
        else return List.of(
                new GeoPoint(this,ray.getTargetPoint(direction1)));

    }

}
