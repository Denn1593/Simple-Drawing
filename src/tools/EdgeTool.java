package tools;

import javafx.scene.paint.Color;
import tools.util.ToolUtilities;

/**
 * Created by Dennis on 18-11-2016.
 */
public class EdgeTool implements Tool {

    private int[] xPositions = {-1, 0, 1, -1, 1, -1, 0, 1};
    private int[] yPositions = {-1, -1, -1, 0, 0, 1, 1, 1};

    private Color[][] colors;

    @Override
    public boolean[][] erase(int size, Color color, Color[][] inData)
    {
        return new boolean[size][size];
    }

    @Override
    public boolean[][] eraseAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        return new boolean[width][height];
    }

    @Override
    public boolean canRead() {
        return true;
    }

    @Override
    public Color[][] paint(int size, Color color, Color[][] inData)
    {
        colors = copy(inData, size, size, color);
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(inData[x][y] != null && inData[x][y] != color)
                {
                    if (isEdgePixel(x, y, color))
                    {
                        for (int xx = 0; xx < size; xx++)
                        {
                            for (int yy = 0; yy < size; yy++)
                            {
                                try
                                {
                                    if (ToolUtilities.isInRange(xx, yy, size) && ToolUtilities.isInRange(x + xx - size / 2, y + yy - size / 2, size))
                                    {
                                        if (inData[x + xx - size / 2][y + yy - size / 2] == null)
                                        {
                                            inData[x + xx - size / 2][y + yy - size / 2] = color;
                                        }
                                    }
                                }
                                catch (Exception e)
                                {

                                }
                            }
                        }
                    }
                }
            }
        }
        return inData;
    }

    @Override
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY)
    {
        colors = copy(inData, width, height, color);
        Color targetColor = inData[mouseX][mouseY];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if(inData[x][y] != null && inData[x][y] != color)
                {
                    if (isEdgePixel(x, y, color))
                    {
                        if(targetColor == null || (inData[x][y] == targetColor))
                        {
                            for (int xx = 0; xx < size; xx++)
                            {
                                for (int yy = 0; yy < size; yy++)
                                {
                                    try
                                    {
                                        if (ToolUtilities.isInRange(xx, yy, size))
                                        {
                                            if (inData[x + xx - size / 2][y + yy - size / 2] == null)
                                            {
                                                inData[x + xx - size / 2][y + yy - size / 2] = color;
                                            }
                                        }
                                    } catch (Exception e)
                                    {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return inData;
    }

    private boolean isEdgePixel(int x, int y, Color color)
    {
        boolean isEdgePixel = false;

        for (int i = 0; i < xPositions.length; i++)
        {
            try
            {
                if(colors[x + xPositions[i]][y + yPositions[i]] == null && colors[x + xPositions[i]][y + yPositions[i]] != color)
                {
                    isEdgePixel = true;
                    break;
                }
            }
            catch (Exception e)
            {

            }
        }
        return isEdgePixel;
    }

    private Color[][] copy(Color[][] colors, int width, int height, Color color)
    {
        Color[][] copy = new Color[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if(colors[i][j] != color)
                {
                    copy[i][j] = colors[i][j];
                }
            }
        }
        return copy;
    }

    public String toString()
    {
        return "EdgeTool";
    }
}

/* DEPRECATED

                if(inData[x][y] == null && ToolUtilities.isInRange(x, y, size))
                {
                    for (int xx = 0; xx < size; xx++)
                    {
                        for (int yy = 0; yy < size; yy++)
                        {
                            try
                            {
                                if(ToolUtilities.isInRange(xx, yy, size))
                                {
                                    if (inData[x + xx - size / 2][y + yy - size / 2] != null &&
                                            inData[x + xx - size / 2][y + yy - size / 2] != color)
                                    {
                                        inData[x][y] = color;
                                    }
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }
                }


                if(inData[x][y] == null)
                {
                    for (int xx = 0; xx < size; xx++)
                    {
                        for (int yy = 0; yy < size; yy++)
                        {
                            try
                            {
                                if (ToolUtilities.isInRange(xx, yy, size))
                                {
                                    if (inData[x + xx - size / 2][y + yy - size / 2] != null &&
                                            inData[x + xx - size / 2][y + yy - size / 2] != color)
                                    {
                                        inData[x][y] = color;
                                    }
                                }
                            } catch (Exception e)
                            {

                            }
                        }
                    }
                }
 */
