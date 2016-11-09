package tools;

import javafx.scene.paint.Color;

/**
 * Created by dennis on 11/9/16.
 */
public class RecolorTool implements Tool
{
    @Override
    public boolean canRead()
    {
        return true;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(isInRange(x, y, size))
                {
                    if (inData[x][y] != null)
                    {
                        inData[x][y] = color;
                    }
                }
            }
        }
        return inData;
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
        return "RecolorTool";
    }
}
