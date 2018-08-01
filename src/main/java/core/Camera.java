package core;

import math.Matrix;
import math.Matrix4f;
import math.Transform4f;
import math.Vector3f;

public class Camera
{
    public static final Vector3f INIT_FORWARD = new Vector3f(0, 0, 1f);
    public static final Vector3f INIT_UP = new Vector3f(0, 1f, 0);
    public static final Vector3f INIT_POSITION = new Vector3f(0, 0, 0);

    private Vector3f forward;
    private Vector3f up;
    private Vector3f position;

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f viewProjectionMatrix;

    private Transform4f pose;
    private Transform4f velocity;

    public Camera()
    {
        this.forward = INIT_FORWARD;
        this.up = INIT_UP;
        this.position = new Vector3f(0,2,0);

        this.viewMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
        this.viewProjectionMatrix = new Matrix4f();

        this.velocity = new Transform4f();
        this.pose = new Transform4f();

        Matrix4f.loadView(viewMatrix, forward, up, INIT_POSITION);
        Matrix4f.loadPersp(projectionMatrix, 90, 1920, 1080, 0.01f, 100f);
    }

    public Vector3f getPosition()
    {
        return this.position;
    }

    public Matrix4f getProjectionView()
    {

        Matrix4f.loadView(viewMatrix, forward, up, INIT_POSITION);

        update();

        viewMatrix.getMatrix().mul(projectionMatrix.getMatrix(), viewProjectionMatrix.getMatrix());

        return this.viewProjectionMatrix;
    }

    /*
    Every frame we execute this
     */
    public void update()
    {
        this.velocity.applyTransform(false); /* Build our combined transformation matrix for velocity */
        this.position.getMatrix().mulInPlace(velocity.getTransformation().getMatrix()); /* Every frame, apply the velocity transformation to our position */
        this.pose.translate(position); /* Translate the pose to the current position */
        this.pose.applyTransform(false); /* Build our combined transformation matrix for the camera's pose */

        viewMatrix.getMatrix().mulInPlace(this.pose.getTransformation().getMatrix()); /* Every frame, apply the pose transformation to the view matrix which will translate the world */
    }

    public void move(Vector3f direction)
    {
        direction.getMatrix().mulInPlace(this.pose.getRotation().getMatrix().transpose(new Matrix(4,4))); /* */
        System.out.println(direction.getMatrix());
        this.velocity.translate(direction);
    }

    public void rotate(Vector3f rotation)
    {
        this.pose.rotate(rotation);
    }
}
