package files;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by dennis on 11/8/16.
 */
public class ImageExporter
{
    public static boolean exportImage(File file, Image image)
    {
        try
        {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
