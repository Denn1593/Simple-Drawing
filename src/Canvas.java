import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dennis on 11/7/16.
 */
public class Canvas extends ImageView
{
    private ArrayList<Layer> layers = new ArrayList<>();
    private PixelWriter pixelWriter;
    private int width;
    private int height;
    private Tool tool = new SprayTool();
    private Window window;

    public Canvas(int width, int heigth, Window window)
    {
        this.window = window;
        this.width = width;
        this.height = heigth;

        WritableImage image = new WritableImage(width, heigth);
        pixelWriter = image.getPixelWriter();

        this.setImage(image);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < heigth; y++)
            {
                pixelWriter.setColor(x, y, Color.WHITE);
            }
        }

        layers.add(new Layer(width, heigth, this, "Layer1"));

        this.setOnMouseDragged(e->
        {
            try {
                paint((int) e.getX(), (int) e.getY());
                window.setGlow(null);
            }
            catch (Exception ex)
            {
                window.setGlow(new DropShadow(5, Color.RED));
            }
        });
    }

    public void updateCanvas(int xStart, int yStart, int xEnd, int yEnd)
    {
        for (int i = layers.size() - 1; i >= 0; i--)
        {
            for(int x = xStart; x < xEnd; x++)
            {
                for (int y = yStart; y < yEnd; y++)
                {
                    if(layers.get(i).getColor(x, y) != null)
                    {
                        pixelWriter.setColor(x, y, layers.get(i).getColor(x, y));
                    }
                }
            }
        }
    }

    private void paint(int x, int y)
    {
        int size = window.getSize();
        layers.get(window.getLayer()).paint(x - size / 2, y - size / 2, tool.paint(size, window.getColor()), size);
    }

    public void moveDown(int index)
    {
        if(index < layers.size() - 1)
        {
            Collections.swap(layers, index, index + 1);
        }
        updateCanvas(0, 0, width, height);
    }

    public void moveUp(int index)
    {
        if(index > 0)
        {
            Collections.swap(layers, index, index - 1);
        }
        updateCanvas(0, 0, width, height);
    }

    public ArrayList<Layer> destroyLayer(int index)
    {
        layers.remove(index);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                pixelWriter.setColor(x, y, Color.WHITE);
            }
        }
        updateCanvas(0, 0, width, height);

        return layers;
    }

    public ArrayList<Layer> getLayers()
    {
        return layers;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
