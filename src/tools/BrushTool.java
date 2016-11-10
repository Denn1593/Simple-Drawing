package tools;

import javafx.scene.paint.Color;

/**
 * Created by dennis on 11/8/16.
 */
public class BrushTool implements Tool
{


    @Override
    public boolean[][] erase(int size, Color color, Color[][] inData)
    {
        return new boolean[size][size];
    }

    @Override
    public boolean canRead()
    {
        return false;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        Color[][] colors = new Color[size][size];

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(isInRange(x, y, size))
                {
                    colors[x][y] = color;
                }
            }
        }
        return colors;
    }

    private boolean isInRange(int x, int y, int size)
    {
        if(Math.sqrt((x - size/2) * (x - size/2) + (y - size/2) * (y - size/2)) < size/2)
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return "BrushTool";
    }


}
