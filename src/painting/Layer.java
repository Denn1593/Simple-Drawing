package painting;

import javafx.scene.paint.Color;

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

    public void paint(int x, int y, Color[][] colors, int size, boolean[][] erased)
    {
        boolean hasErased = false;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
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
        canvas.updateCanvas(x, y, x + size, y + size, hasErased);
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

    public int[] getDimensions()
    {
        int[] dimensions = {width, height};
        return dimensions;
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
