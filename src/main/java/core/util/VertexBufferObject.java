package core.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBufferObject
{
    private final int id;

    public VertexBufferObject()
    {
        id = glGenBuffers();
    }

    public void bind(int target)
    {
        glBindBuffer(target, id);
    }

    public void uploadData(int target, FloatBuffer data, int usage)
    {
        glBufferData(target, data, usage);
    }

    public void uploadData(int target, IntBuffer data, int usage)
    {
        glBufferData(target, data, usage);
    }

    public int getID()
    {
        return this.id;
    }
}
