package painting.layers;

import effects.base.LayerEffect;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import painting.Canvas;

import java.io.Serializable;

/**
 * Created by dennis on 11/7/16.
 */
public class Layer implements Serializable
{
    private Canvas canvas;
    private Color[][] layer;
    private int width;
    private int height;
    private String name;

    public Layer(int width, int height, Canvas canvas, String name)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.canvas = canvas;
        layer = new Color[width][height];
    }

    public Layer(Color[][] colors, int width, int height, String name)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        layer = colors;
    }

    public void paint(int x, int y, Color[][] colors, int size, boolean[][] erased, int w, int h)
    {
        boolean hasErased = false;

        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                if(i + x >= 0 && i + x < width && j + y >= 0 && j + y < height)
                {
                    if(colors[i][j] != null)
                    {
                        layer[i + x][j + y] = colors[i][j];
                    }
                    if(erased[i][j])
                    {
                        hasErased = true;
                        layer[i + x][j + y] = null;
                    }
                }
            }
        }
        canvas.updateCanvas(x, y, x + w, y + h, hasErased);
    }

    public Color getColor(int x, int y)
    {
        try
        {
            return layer[x][y];
        }
        catch (Exception e)
        {
            return  null;
        }
    }

    public void shiftLayer(KeyEvent direction, boolean isShiftPressed)
    {
        int factor = 1;

        if(isShiftPressed)
        {
            factor = 10;
        }

        System.out.println(factor);

        switch (direction.getCode())
        {
            case UP:
            {
                Color[][] buffer = new Color[width][factor];

                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        buffer[i][j] = layer[i][j];
                    }
                }

                for (int i = 0; i < height - factor; i++)
                {
                    for (int j = 0; j < width; j++)
                    {
                        layer[j][i] = layer[j][i + factor];
                    }
                }

                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        layer[i][height - factor + j] = buffer[i][j];
                    }
                }
                break;
            }
            case DOWN:
            {
                Color[][] buffer = new Color[width][factor];

                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        buffer[i][j] = layer[i][j + height - factor];
                    }
                }

                for (int i = height - factor - 1; i >= 0; i--)
                {
                    for (int j = 0; j < width; j++)
                    {
                        layer[j][i + factor] = layer[j][i];
                    }
                }

                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        layer[i][j] = buffer[i][j];
                    }
                }
                break;
            }
            case LEFT:
            {
                Color[][] buffer = new Color[factor][height];

                for (int i = 0; i < height; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        buffer[j][i] = layer[j][i];
                    }
                }

                for (int i = 0; i < width - factor; i++)
                {
                    for (int j = 0; j < height; j++)
                    {
                        layer[i][j] = layer[i + factor][j];
                    }
                }

                for (int i = 0; i < height; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        layer[width - factor + j][i] = buffer[j][i];
                    }
                }
                break;
            }
            case RIGHT:
            {
                Color[][] buffer = new Color[factor][height];

                for (int i = 0; i < height; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        buffer[j][i] = layer[j + width - factor][i];
                    }
                }

                for (int i = width - factor - 1; i >= 0; i--)
                {
                    for (int j = 0; j < height; j++)
                    {
                        layer[i + factor][j] = layer[i][j];
                    }
                }

                for (int i = 0; i < height; i++)
                {
                    for (int j = 0; j < factor; j++)
                    {
                        layer[j][i] = buffer[j][i];
                    }
                }
                break;
            }
        }
    }

    public void applyEffect(LayerEffect effect)
    {
        layer = effect.applyEffect(layer, width, height);
    }

    public int[] getDimensions()
    {
        int[] dimensions = {width, height};
        return dimensions;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    public String toString()
    {
        return name;
    }
}
