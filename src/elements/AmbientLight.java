package elements;

import primitives.Color;

public class AmbientLight {

    private Color _intensity;
   // private double _Ka;         // Sets the intensity of the light

    //***************** Constructors **********************//


    /**
     * @param _intensity Filling Light Intensity
     * @param _Ka Sets the intensity of the light
     */
    public AmbientLight(Color _intensity, double _Ka) {
        this._intensity = _intensity;
    }
    
}
