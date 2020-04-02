package unittests;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Planes
 * @author Yossef and Shmuel
 */
public class CylinderTests {

    /**
     * Test method for
     * {@link Cylinder#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        Vector v = new Vector(new Point3D(0,0,1));
        Ray r =  new Ray(new Point3D(0,0,0), v);
        Cylinder cy = new Cylinder(1.0, r, 2.0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple test
        assertEquals("Wrong Tube getNormal()",new Vector(0,1,0) ,
                cy.getNormal(new Point3D(0,1,1)));

        // TC02: normal from point on base 1
        assertEquals("Wrong Cylinder getNormal() from point on base 1" ,
                v, cy.getNormal(new Point3D(0.5,0,0)));

        //TC03: normal from point on base 2
        assertEquals("Wrong Cylinder getNormal() from point on base 2" ,
                v, cy.getNormal(new Point3D(0.5,0,2.0)));


        // =============== Boundary Values Tests ==================
        //TC04: normal from point on surface and base 1
        assertEquals("Wrong Cylinder getNormal() from point on base 1" ,
                v, cy.getNormal(new Point3D(0,1,0)));

        //TC05: normal from point on surface and base 2
        assertEquals("Wrong Cylinder getNormal() from point on base 1" ,
                v, cy.getNormal(new Point3D(0,1,2)));
    }
}