package effects.base;

/**
 * Created by Dennis on 29-06-2017.
 */
public class Parameter
{
    private double lower;
    private double upper;
    private double value;
    private String name;

    public Parameter(double lower, double upper, String name)
    {
        this.name = name;
        this.lower = lower;
        this.upper = upper;
        this.value = lower;
    }

    public String getName()
    {
        return name;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public double getLower()
    {
        return lower;
    }

    public double getUpper()
    {
        return upper;
    }
}
