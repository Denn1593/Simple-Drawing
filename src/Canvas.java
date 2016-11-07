import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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

        layers.add(new Layer(width, heigth, this));

        this.setOnMouseDragged(e-> paint((int) e.getX(), (int) e.getY()));
    }

    public void updateCanvas(int xStart, int yStart, int xEnd, int yEnd)
    {
        for (int i = 0; i < layers.size(); i++)
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
