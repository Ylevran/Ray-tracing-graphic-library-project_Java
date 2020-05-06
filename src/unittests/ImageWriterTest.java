package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Write to Image (Color pixels) Test
 *
 * @author Shmuel & Yossef
 */
public class ImageWriterTest {


    @Test
    public void writeToImage() {

        // TC01: Simple grid test
        String imageName = "Test 01 for writeToImage";

        //the size of the view plane
        double width = 1600;
        double height = 1000;

        //the resolution
        int nX = 800;
        int nY = 500;

        ImageWriter imageWriter = new ImageWriter(imageName, width, height, nX, nY);

        for (int column = 0; column < nY; ++column) {
            for (int row = 0; row < nX; ++row) {
                if ((column % 50 == 0 && column > 0) || (row % 50 == 0 && row >0)) {
                    imageWriter.writePixel(row, column, Color.pink);
                }
                else {
                    imageWriter.writePixel(row,column, Color.gray);
                }
            }
        }
        imageWriter.writeToImage();

    }
}