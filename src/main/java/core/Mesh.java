package core;

import core.util.VertexArrayObject;

public class Mesh
{
    private VertexArrayObject vao;
    private int vertexCount;

    public Mesh(VertexArrayObject vao, int vertexCount)
    {
        this.vao = vao;
        this.vertexCount = vertexCount;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public int getVAOID()
    {
        return vao.getID();
    }
}
