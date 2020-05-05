package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {
        String imageName = "The best project";
        ImageWriter writer = new ImageWriter(imageName,500,500,500,500);

    }
}