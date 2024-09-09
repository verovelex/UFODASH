package UfoDash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Abstract base class for drawable objects that provide utility
 * methods such as image loading.
 */
public abstract class AbstractDrawable implements Drawable{
    /**
     * Loads an image from the given path.
     *
     * @param imagePath The path to the image file
     * @return the loaded image, or null if the image can't be found or loaded
     */
    protected Image loadImage(String imagePath){
        Image img = null;
        try {
            URL imageURL = ClassLoader.getSystemResource("images/" +  imagePath);
            if(imageURL != null){
                img = ImageIO.read(imageURL);
                if(img == null){
                    System.err.println("Failed to load image: " + imagePath);
                }
            }else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return img;
        }
        return img;
    }
}
