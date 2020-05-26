package primitives;

/**
 *
 * @author Yossef Levran, ID: 332484609, Email Address: yossef.levran@gmail.com
 *
 * @author Shmuel Segal, ID: 052970464, Email address: shmuelse@gmail.com
 */
public class Material {

    private double _kD;
    private double _kS;
    private int _nShininess;

    //***************** Constructors **********************//

    /**
     * @param _kD
     * @param _kS
     * @param _nShininess
     *                  - Determines the level of shining of the material
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * Copy constructor
     *
     * @param material
     */
    public Material(Material material){
        this(material._kD, material._kS, material._nShininess);
    }
    // ***************** Getters/Setters ********************** //

    /**
     * Getter
     *
     * @return
     */
    public double getKd() {
        return _kD;
    }

    /**
     * Getter
     *
     * @return
     */
    public double getKs() {
        return _kS;
    }


    /**
     * Getter
     * Gets the degree of shining of the material
     * @return Exponent of shininess
     */
    public int getNShininess() {
        return _nShininess;
    }

}
