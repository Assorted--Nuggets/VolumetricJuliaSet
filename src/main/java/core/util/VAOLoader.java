package core.util;

import sun.security.provider.certpath.Vertex;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class VAOLoader
{
    private List<VertexArrayObject> vaos = new ArrayList<>();
    private List<VertexBufferObject> vbos = new ArrayList<>();

    public VertexArrayObject createVAO()
    {
        VertexArrayObject toAdd = new VertexArrayObject();
        vaos.add(toAdd);
        toAdd.bind();
        return toAdd;
    }

    public VertexBufferObject createVBO()
    {
        VertexBufferObject toAdd = new VertexBufferObject();
        vbos.add(toAdd);
        return toAdd;
    }

    public void storeToAttribute(int attribLocation, Float[] data)
    {
        VertexBufferObject vbo = createVBO();
        vbo.bind(GL_ARRAY_BUFFER);
        vbo.uploadData(GL_ARRAY_BUFFER, BufferHelper.storeToFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attribLocation, 3, GL_FLOAT, false, 0, 0);
    }

    public void bindIndexBuffer(Integer[] indices)
    {
        VertexBufferObject vbo = createVBO();
        vbo.bind(GL_ELEMENT_ARRAY_BUFFER);
        vbo.uploadData(GL_ELEMENT_ARRAY_BUFFER, BufferHelper.storeToIntBuffer(indices), GL_STATIC_DRAW);
    }

    public void unbindVAO()
    {
        glBindVertexArray(0);
    }
}
