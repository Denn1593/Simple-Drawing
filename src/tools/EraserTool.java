package tools;

import javafx.scene.paint.Color;

/**
 * Created by dennis on 11/10/16.
 */
public class EraserTool implements Tool
{
    @Override
    public boolean[][] erase(int size, Color color, Color[][] inData)
    {
        boolean[][] erase = new boolean[size][size];

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(isInRange(x, y, size))
                {
                    erase[x][y] = true;
                }
            }
        }
        return erase;
    }

    @Override
    public boolean canRead()
    {
        return false;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
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
}
