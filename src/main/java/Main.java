import core.Camera;
import core.CoreEngine;
import math.Matrix4f;
import math.Transform4f;
import math.Vector3f;

public class Main
{
    public static CoreEngine engine;
    public static void main(String[] args)
    {
        engine = new CoreEngine();
        engine.start();

//        Matrix4f rotation = new Matrix4f();
//        Matrix4f.loadRotation(rotation, new Vector3f(0, 0, -45f));
//
//        Vector3f v = new Vector3f(1, 0, 0);
//        v.getMatrix().mulInPlace(rotation.getMatrix());
//        System.out.println(v.getMatrix());

    }
}
