package math;

public class Matrix4f
{
    private Matrix values;

    public Matrix4f()
    {
        this.values = new Matrix(4, 4);
    }

    public static Matrix4f loadOrtho(Matrix4f dest, float left, float right, float bot, float top, float near, float far)
    {
        dest.values.loadZeros();

        float width = right-left;
        float height = top-bot;

        dest.values.setElement(2.0f/width, 0, 0);
        dest.values.setElement(-(right+left)/(width), 3, 0);
        dest.values.setElement(2.0f/height, 1, 1);
        dest.values.setElement(-(top+bot)/height, 3, 1);
        dest.values.setElement(-2.0f/(far-near), 2, 2);
        dest.values.setElement(-(far+near)/(far-near), 3, 2);
        dest.values.setElement(1, 3, 3);

        return dest;
    }

    public static Matrix4f loadPerspective(Matrix4f dest, float left, float right, float bot, float top, float near, float far)
    {
        dest.values.loadZeros();

        float width = right-left;
        float height = top-bot;

        dest.values.setElement((2.0f*near)/width, 0, 0);
        dest.values.setElement((right+left)/(right-left), 2, 0);
        dest.values.setElement((2.0f*near)/height, 1, 1);
        dest.values.setElement((top+bot)/height, 2, 1);

        dest.values.setElement(-(far+near)/(far-near), 2, 2);
        dest.values.setElement(-(2.0f*far*near)/(far-near), 3, 2);
        dest.values.setElement(-1, 2, 3);

        return dest;
    }

    public static Matrix4f loadPersp(Matrix4f dest, float fov, float width, float height, float near, float far)
    {
        float halfHeight = FloatMath.tan(FloatMath.toRadians(fov/2) * near);

        float halfScaledAspect = halfHeight * (width/height);

        return loadPerspective(dest, -halfScaledAspect, halfScaledAspect, -halfHeight, halfHeight, near, far);
    }

    public static Matrix4f loadView(Matrix4f dest, Vector3f forward, Vector3f up, Vector3f pos)
    {
        Vector3f f = forward;
        Vector3f u = up;
        Vector3f r = u.cross(f).getNorm();


        dest.values.loadIdentity();
        dest.values.setElement(r.getX(), 0, 0);
        dest.values.setElement(r.getY(), 0, 1);
        dest.values.setElement(r.getZ(), 0, 2);

        dest.values.setElement(u.getX(), 1, 0);
        dest.values.setElement(u.getY(), 1, 1);
        dest.values.setElement(u.getZ(), 1, 2);

        dest.values.setElement(f.getX(), 2, 0);
        dest.values.setElement(f.getY(), 2, 1);
        dest.values.setElement(f.getZ(), 2, 2);

        float t = -pos.dot(r);
        float v = -pos.dot(u);
        float q = -pos.dot(f);

        dest.values.setElement(t, 3, 0);
        dest.values.setElement(v, 3, 1);
        dest.values.setElement(q, 3, 2);
        return dest;
    }

    public static Matrix4f loadTranslation(Matrix4f dest, Vector3f translation)
    {
        dest.values.loadIdentity();
        dest.values.setElement(translation.getX(), 3, 0);
        dest.values.setElement(translation.getY(), 3, 1);
        dest.values.setElement(translation.getZ(), 3, 2);

        return dest;
    }

    public static Matrix4f loadRotation(Matrix4f dest, Vector3f rotation)
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();
        rx.values.loadIdentity();
        ry.values.loadIdentity();
        rz.values.loadIdentity();

        float x = FloatMath.toRadians(rotation.getX());
        float y = FloatMath.toRadians(rotation.getY());
        float z = FloatMath.toRadians(rotation.getZ());

        rz.values.setElement(FloatMath.cos(z), 0, 0);
        rz.values.setElement(-FloatMath.sin(z), 0, 1);
        rz.values.setElement(FloatMath.sin(z), 1, 0);
        rz.values.setElement(FloatMath.cos(z), 1, 1);

        rx.values.setElement(FloatMath.cos(x), 1, 1);
        rx.values.setElement(-FloatMath.sin(x), 1, 2);
        rx.values.setElement(FloatMath.sin(x), 2, 1);
        rx.values.setElement(FloatMath.cos(x), 2, 2);

        ry.values.setElement(FloatMath.cos(y), 0, 0);
        ry.values.setElement(FloatMath.sin(y), 0, 2);
        ry.values.setElement(-FloatMath.sin(y), 2, 0);
        ry.values.setElement(FloatMath.cos(y), 2, 2);

        dest.values = rz.values.mulInPlace(ry.values.mulInPlace(rx.values));
        return dest;
    }

    public static Matrix4f loadScale(Matrix4f dest, Vector3f scaleVal)
    {
        dest.values.loadIdentity();

        dest.values.setElement(scaleVal.getX(), 0, 0);
        dest.values.setElement(scaleVal.getY(), 1, 1);
        dest.values.setElement(scaleVal.getZ(), 2, 2);

        return dest;
    }

    public static Matrix4f loadOrtho(Matrix4f dest, float width, float height)
    {
        dest.values.loadZeros();

        dest.values.setElement(2.0f/width, 0, 0);
        dest.values.setElement(-1.0f, 0, 3);
        dest.values.setElement(2.0f/height, 1, 1);
        dest.values.setElement(-1, 1, 3);
        dest.values.setElement(1, 2, 2);
        dest.values.setElement(1, 3, 3);

        return dest;
    }

    public Matrix getMatrix()
    {
        return values;
    }

    public String toString()
    {
        return "4 x 4 Matrix \n" + values.toString();
    }
}
