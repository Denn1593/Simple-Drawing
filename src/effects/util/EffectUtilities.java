package effects.util;

/**
 * Created by Dennis on 30-06-2017.
 */
public class EffectUtilities
{
    public static int[] getClosestPixel(int x, int y, int width, int height)
    {
        int[] coordinates = new int[2];
        if(x < 0 && y < 0)
        {
            coordinates[0] = 0;
            coordinates[1] = 0;
            return coordinates;
        }
        if(x >= 0 && x <= width - 1 && y < 0)
        {
            coordinates[0] = x;
            coordinates[1] = 0;
            return coordinates;
        }
        if(x > width - 1 && y < 0)
        {
            coordinates[0] = width - 1;
            coordinates[1] = 0;
            return coordinates;
        }
        if(y >= 0 && y <= height - 1 && x > width - 1)
        {
            coordinates[0] = width - 1;
            coordinates[1] = y;
            return coordinates;
        }
        if(y > height - 1 && x > width - 1)
        {
            coordinates[0] = width - 1;
            coordinates[1] = height - 1;
            return coordinates;
        }
        if(x >= 0 && x <= width - 1 && y > height - 1)
        {
            coordinates[0] = x;
            coordinates[1] = height - 1;
            return coordinates;
        }
        if(x < 0 && y > height - 1)
        {
            coordinates[0] = 0;
            coordinates[1] = height - 1;
            return coordinates;
        }
        if(y >= 0 && y <= height - 1 && x < 0)
        {
            coordinates[0] = 0;
            coordinates[1] = y;
            return coordinates;
        }
        System.out.println(x);
        System.out.println(y);
        return null;
    }
}
