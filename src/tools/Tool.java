package tools;

import javafx.scene.paint.Color;

/**
 * Created by dennis on 11/7/16.
 */
public interface Tool
{
    public boolean[][] erase(int size, Color color, Color[][] inData);
    public boolean[][] eraseAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY);
    public boolean canRead();
    public Color[][] paint(int size, Color color, Color[][] inData);
    public Color[][] paintAll(int size, Color color, Color[][] inData, int width, int height, int mouseX, int mouseY);
}
