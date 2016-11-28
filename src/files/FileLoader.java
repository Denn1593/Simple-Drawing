package files;

import files.formats.CanvasFile;
import files.formats.javafxworkarounds.LayerConverter;
import files.formats.javafxworkarounds.SerializableLayer;
import painting.Layer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by dennis on 11/8/16.
 */
public class FileLoader
{
    //TODO: how to save javafx nodes
    public static boolean saveFile(CanvasFile canvasFile)
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(canvasFile.getFileName() + ".can")));
            out.writeObject(canvasFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
                e.printStackTrace();
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
            e.printStackTrace();
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
                e.printStackTrace();
                return null;
            }
        }

        System.out.println(canvasFile.getDimensions()[0] + "ass");
        System.out.println(canvasFile.getDimensions()[1] + "ass");
        return LayerConverter.serializableToLayers(canvasFile);
    }
}
