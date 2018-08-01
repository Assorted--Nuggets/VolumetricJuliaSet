package math;

public class Transform4f
{
    private Matrix4f translation;
    private Matrix4f rotation;
    private Matrix4f scale;

    private Matrix4f transformation;

    public Transform4f()
    {
        this.translation = new Matrix4f();
        this.rotation = new Matrix4f();
        this.scale = new Matrix4f();
        this.transformation = new Matrix4f();

        this.translation.getMatrix().loadIdentity();
        this.rotation.getMatrix().loadIdentity();
        this.transformation.getMatrix().loadIdentity();
        this.scale.getMatrix().loadIdentity();
    }

    public void applyTransform(boolean rotateFirst)
    {
        this.transformation.getMatrix().loadZeros();
        this.transformation.getMatrix().loadIdentity();
        //System.out.println(this.transformation);

        if(rotateFirst)
        {
            this.transformation.getMatrix().mulInPlace(this.rotation.getMatrix());
            this.transformation.getMatrix().mulInPlace(this.translation.getMatrix());
            this.transformation.getMatrix().mulInPlace(this.scale.getMatrix());
        }
        else
        {
            this.transformation.getMatrix().mulInPlace(this.translation.getMatrix());
            this.transformation.getMatrix().mulInPlace(this.rotation.getMatrix());
            this.transformation.getMatrix().mulInPlace(this.scale.getMatrix());
        }

    }

    public void rotate(Vector3f r)
    {
        Matrix4f.loadRotation(rotation, r);
    }

    public void translate(Vector3f t)
    {
        Matrix4f.loadTranslation(translation, t);
    }

    public void scale(Vector3f s)
    {
        Matrix4f.loadScale(scale, s);
    }

    public Matrix4f getTranslation()
    {
        return translation;
    }

    public void setTranslation(Matrix4f translation)
    {
        this.translation = translation;
    }

    public Matrix4f getRotation()
    {
        return rotation;
    }

    public void setRotation(Matrix4f rotation)
    {
        this.rotation = rotation;
    }

    public Matrix4f getScale()
    {
        return scale;
    }

    public void setScale(Matrix4f scale)
    {
        this.scale = scale;
    }

    public Matrix4f getTransformation()
    {
        return transformation;
    }

    public void setTransformation(Matrix4f transformation)
    {
        this.transformation = transformation;
    }
}
