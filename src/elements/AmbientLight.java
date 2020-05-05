package elements;

import primitives.Color;

public class AmbientLight {

    private Color _intensity;



    // ***************** Getters/Setters ********************** //


    /**
     * Getter
     *
     * @return the intensity of the light
     */
    public java.awt.Color getIntensity() {
        return _intensity.getColor();
    }

    /**
     * Setter
     *
     * @param _intensity
     *                  -
     */
    public void setIntensity(Color _intensity) {
        this._intensity = _intensity;
    }

   // private double _Ka;         // Sets the intensity of the light

    //***************** Constructors **********************//


    /**
     * @param _intensity Filling Light Intensity
     * @param _Ka Sets the intensity of the light
     */
    public AmbientLight(Color _intensity, double _Ka) {
        // the value of _Ka is always one, so We do not consider this parameter.
        this._intensity = _intensity;
    }

}
