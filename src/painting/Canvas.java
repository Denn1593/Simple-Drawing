package painting;

import effects.base.LayerEffect;
import gui.Window;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import painting.layers.Layer;

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

        javafxWorkaroundnumber298472372545896();

        this.setOnMouseDragged(e->
        {
            if(e.getButton() == MouseButton.PRIMARY)
            {
                try
                {
                    paint((int) e.getX(), (int) e.getY());
                    window.setGlow(null);
                } catch (Exception ex)
                {
                    window.setGlow(new DropShadow(5, Color.RED));
                    ex.printStackTrace();
                }
            }
            window.setShowColor(e);
        });

        this.setOnMouseClicked(e->
        {
            if(e.getButton() == MouseButton.SECONDARY)
            {
                try {
                    paintAll((int) e.getX(), (int) e.getY());
                    window.setGlow(null);
                }
                catch (Exception ex)
                {
                    window.setGlow(new DropShadow(5, Color.RED));
                    ex.printStackTrace();
                }
            }
            if(e.getButton() == MouseButton.MIDDLE)
            {
                window.setPaintColor(getLayers().get(window.getLayer()).getColor((int) e.getX(), (int) e.getY()));
            }
        });

        this.setOnMouseReleased(e->
        {
            updateCanvas(0, 0, width, heigth, true);
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
                        if(getCheckered(x, y))
                        {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        }
                        else
                        {
                            pixelWriter.setColor(x, y, Color.color(0.9, 0.9, 0.9));
                        }
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
                window.getTool().erase(size, window.getColor(), inData), size, size);
    }

    private void paintAll(int mouseX, int mouseY)
    {
        int size = window.getSize();

        Color[][] inData = new Color[width][height];

        if(window.getTool().canRead())
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    if(layers.get(window.getLayer()).getColor(x, y) != null)
                    {
                        inData[x][y] = layers.get(window.getLayer()).getColor(x, y);
                    }
                }
            }
        }

        layers.get(window.getLayer()).paint(0, 0,
                window.getTool().paintAll(size, window.getColor(), inData, width, height, mouseX, mouseY), size,
                window.getTool().eraseAll(size, window.getColor(), inData, width, height, mouseX, mouseY), width, height);
    }

    public void applyEffect(LayerEffect effect)
    {
        layers.get(window.getLayer()).applyEffect(effect);
        updateCanvas(0, 0, width, height, true);
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
        javafxWorkaroundnumber298472372545896();
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

    //because javafx is fucking GENIUS!!!

    private void javafxWorkaroundnumber298472372545896()
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if(getCheckered(i, j))
                {
                    pixelWriter.setColor(i, j, Color.WHITE);
                }
                else
                {
                    pixelWriter.setColor(i, j, Color.color(0.9, 0.9, 0.9));
                }
            }
        }
    }

    private boolean getCheckered(int x, int y)
    {
        if(Math.floor(x / 10) % 2 == 0)
        {
            if(Math.floor((y / 10)) % 2 == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(Math.floor((y / 10)) % 2 == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    public Image getJavafxWorkaroundnumber324923847289()
    {
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        boolean[][] isPainted = new boolean[width][height];

        for(int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int i = 0; i < layers.size(); i++)
                {
                    if (layers.get(i).getColor(x, y) != null)
                    {
                        if (!isPainted[x][y])
                        {
                            pixelWriter.setColor(x, y, layers.get(i).getColor(x, y));
                            isPainted[x][y] = true;
                        }
                    }
                }
            }
        }
        return writableImage;
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
