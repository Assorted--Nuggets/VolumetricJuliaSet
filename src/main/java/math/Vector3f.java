package math;

public class Vector3f
{
    public static final Vector3f X_NORM = new Vector3f(1, 0, 0);
    public static final Vector3f Y_NORM = new Vector3f(0, 1, 0);
    public static final Vector3f Z_NORM = new Vector3f(0, 0, 1);
    public static final Vector3f ORIGIN = new Vector3f(0, 0, 0);
    private Matrix values;

    public Vector3f(float x, float y, float z)
    {
        values = new Matrix(1, 4);
        setX(x);
        setY(y);
        setZ(z);
        values.setElement(1, 0, 3);
    }

    public float dot(Vector3f v)
    {
        return getX()*v.getX() + getY()*v.getY() + getZ()*v.getZ();
    }

    public Vector3f cross(Vector3f v)
    {
        Vector3f res = new Vector3f(0, 0, 0);
        res.setX((getY()*v.getZ() - getZ()*v.getY()));
        res.setY((getZ()*v.getX() - getX()*v.getZ()));
        res.setZ((getX()*v.getY() - getY()*v.getX()));
        return res;
    }

    public float getLength()
    {
        return FloatMath.sqrt(dot(this));
    }

    public Vector3f getNorm()
    {
        float len = getLength();
        return new Vector3f(getX()/len, getY()/len, getZ()/len);
    }

    public Vector3f normalize()
    {
        this.values.divInPlace(getLength());
        return this;
    }

    public Vector3f copy()
    {
        return new Vector3f(this.getX(), this.getY(), this.getZ());
    }

    // AUTO GENERATED CODE

    public float getX()
    {
        return values.getElement(0,0);
    }

    public void setX(float x)
    {
        values.setElement(x, 0, 0);
    }

    public float getY()
    {
        return values.getElement(0, 1);
    }

    public void setY(float y)
    {
        values.setElement(y, 0, 1);
    }

    public float getZ()
    {
        return values.getElement(0, 2);
    }

    public void setZ(float z)
    {
        values.setElement(z, 0, 2);
    }

    public Matrix getMatrix()
    {
        return this.values;
    }
}
