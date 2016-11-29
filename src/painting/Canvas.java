package painting;

import gui.Window;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



/**
 * Created by dennis on 11/7/16.
 */
public class Canvas extends ImageView implements Serializable
{
    private ArrayList<Layer> layers = new ArrayList<>();
    private PixelWriter pixelWriter;
    private int width;
    private int height;
    private Window window;

    public Canvas(int width, int heigth, Window window, ArrayList<Layer> layers)
    {
        if(layers != null)
        {
            this.layers = layers;
        }
        else
        {
            this.layers.add(new Layer(width, heigth, this, "Layer1"));
        }
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

        this.setOnMouseDragged(e->
        {
            try {
                paint((int) e.getX(), (int) e.getY());
                window.setGlow(null);
            }
            catch (Exception ex)
            {
                window.setGlow(new DropShadow(5, Color.RED));
                ex.printStackTrace();
            }
        });
    }

    public void updateCanvas(int xStart, int yStart, int xEnd, int yEnd, boolean hasErased)
    {
        boolean[][] isPainted = new boolean[xEnd - xStart][yEnd - yStart];

        for(int x = xStart; x < xEnd; x++)
        {
            for (int y = yStart; y < yEnd; y++)
            {
                boolean allNull = true;
                for (int i = 0; i < layers.size(); i++)
                {
                    if(layers.get(i).getColor(x, y) != null)
                    {
                        allNull = false;
                        if (!isPainted[x - xStart][y - yStart])
                        {
                            pixelWriter.setColor(x, y, layers.get(i).getColor(x, y));
                            isPainted[x - xStart][y - yStart] = true;
                        }
                    }
                }
                if(hasErased && allNull)
                {
                    try
                    {
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }
                    catch (Exception ex)
                    {

                    }
                }
            }
        }
    }

    private void paint(int x, int y)
    {
        int size = window.getSize();

        Color[][] inData = new Color[size][size];

        if(window.getTool().canRead())
        {
            for (int xx =  x - size / 2; xx < x + size / 2; xx++)
            {
                for (int yy =  y - size / 2; yy < y + size / 2; yy++)
                {
                    if (layers.get(window.getLayer()).getColor(xx, yy) != null)
                    {
                        inData[xx - x + size/2][yy - y + size/2] = layers.get(window.getLayer()).getColor(xx, yy);
                    }
                }
            }
        }

        layers.get(window.getLayer()).paint(x - size / 2, y - size / 2,
                window.getTool().paint(size, window.getColor(), inData), size,
                window.getTool().erase(size, window.getColor(), inData));
    }

    public void moveDown(int index)
    {
        if(index < layers.size() - 1)
        {
            Collections.swap(layers, index, index + 1);
        }
        updateCanvas(0, 0, width, height, true);
    }

    public void moveUp(int index)
    {
        if(index > 0)
        {
            Collections.swap(layers, index, index - 1);
        }
        updateCanvas(0, 0, width, height, true);
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
        updateCanvas(0, 0, width, height, true);

        return layers;
    }

    public void fixCanvasPointer(Canvas canvas)
    {
        for (int i = 0; i < layers.size(); i++)
        {
            layers.get(i).setCanvas(canvas);
        }
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
