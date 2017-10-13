package files;

import files.formats.CanvasFile;
import files.formats.javafxworkarounds.LayerConverter;
import painting.layers.Layer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by dennis on 11/8/16.
 */
public class FileLoader
{
    public static boolean saveFile(CanvasFile canvasFile, File file)
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            out.writeObject(canvasFile);
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Layer> loadFile(File file)
    {
        ObjectInputStream in = null;
        CanvasFile canvasFile = null;

        try
        {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            canvasFile = (CanvasFile) in.readObject();
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (Exception e)
            {
                return null;
            }
        }

        return LayerConverter.serializableToLayers(canvasFile);
    }
}
