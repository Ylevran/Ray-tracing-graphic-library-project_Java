package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Tubes
 * @author Yossef and Shmuel
 */
public class TubeTests {

    /**
     * Test method for
     * {@link Tube#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
       Vector v = new Vector(new Point3D(0,0,1));
       Ray r =  new Ray(new Point3D(0,0,0), v);
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
       assertEquals("Wrong Tube getNormal()",new Vector(0,1,0) ,
               new Tube(1.0, r).getNormal(new Point3D(0,1,1)));
    }
}