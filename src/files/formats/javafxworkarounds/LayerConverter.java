package files.formats.javafxworkarounds;

import files.formats.CanvasFile;
import javafx.scene.paint.Color;
import painting.Canvas;
import painting.layers.Layer;

import java.util.ArrayList;

/**
 * Created by dennis on 11/28/16.
 */
public class LayerConverter
{
    public static ArrayList<SerializableLayer> layerToSerializable(Canvas canvas)
    {
        ArrayList<Layer> layers = canvas.getLayers();
        ArrayList<SerializableLayer> convertedLayers = new ArrayList<>();

        for (int i = 0; i < layers.size(); i++)
        {
            Layer layer = layers.get(i);
            int[] dimensions = layer.getDimensions();
            SerializableColor[][] colors = new SerializableColor[dimensions[0]][dimensions[1]];

            for (int x = 0; x < dimensions[0]; x++)
            {
                for (int y = 0; y < dimensions[1]; y++)
                {
                    if(layer.getColor(x, y) != null)
                    {
                        colors[x][y] = new SerializableColor(layer.getColor(x, y).getRed(), layer.getColor(x, y).getGreen(), layer.getColor(x, y).getBlue());
                    }
                }
            }
            convertedLayers.add(new SerializableLayer(colors));
        }

        return convertedLayers;
    }

    public static ArrayList<Layer> serializableToLayers(CanvasFile canvasFile)
    {
        ArrayList<SerializableLayer> layers = canvasFile.getLayers();
        int[] dimensions = canvasFile.getDimensions();
        ArrayList<Layer> realLayers = new ArrayList<>();

        for (int i = 0; i < layers.size(); i++)
        {
            SerializableLayer layer = layers.get(i);
            Color[][] colors = new Color[dimensions[0]][dimensions[1]];

            for (int x = 0; x < dimensions[0]; x++)
            {
                for (int y = 0; y < dimensions[1]; y++)
                {
                    if(layer.getColor(x, y) != null)
                    {
                        double red = layer.getColor(x, y).getColor()[0];
                        double green = layer.getColor(x, y).getColor()[1];
                        double blue = layer.getColor(x, y).getColor()[2];
                        colors[x][y] = Color.color(red, green, blue);
                    }
                }
            }
            realLayers.add(new Layer(colors, dimensions[0], dimensions[1], "Layer"+(i + 1)));
        }

        return realLayers;
    }
}
