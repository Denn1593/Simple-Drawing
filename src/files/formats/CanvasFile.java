package files.formats;


import files.formats.javafxworkarounds.SerializableLayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dennis on 11/25/16.
 */
public class CanvasFile implements Serializable
{
    private ArrayList<SerializableLayer> layers;
    private String fileName;
    private int[] dimensions = new int[2];

    public CanvasFile(ArrayList<SerializableLayer> layers, String name, int width, int height)
    {
        dimensions[0] = width;
        dimensions[1] = height;
        fileName = name;
        this.layers = layers;
    }

    public ArrayList<SerializableLayer> getLayers()
    {
        return layers;
    }

    public int[] getDimensions()
    {
        return dimensions;
    }

    public String getFileName()
    {
        return fileName;
    }
}
