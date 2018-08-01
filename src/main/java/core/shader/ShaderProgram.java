package core.shader;

import math.Matrix4f;
import math.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram
{
    private int programHandle;

    private VertexShader vs;
    private FragmentShader fs;

    private Map<String, Integer> uniforms;

    public ShaderProgram(String vertexSource, String fragmentSource)
    {
        this.programHandle = glCreateProgram();

        this.vs = new VertexShader(vertexSource);
        this.fs = new FragmentShader(fragmentSource);

        this.uniforms = new HashMap<>();

        glAttachShader(programHandle, vs.getShaderHandle());
        glAttachShader(programHandle, fs.getShaderHandle());
    }

    public void link()
    {
        //glBindAttribLocation(vs.getShaderHandle(), 0, "position");
        glLinkProgram(programHandle);
        System.out.println(glGetProgramInfoLog(programHandle, 1024));
    }

    public void use()
    {
        glUseProgram(programHandle);
    }

    public abstract void init();
    public abstract void update();

    public void addUniform(String name)
    {
        uniforms.put(name, glGetUniformLocation(programHandle, name));
    }

    public float[] readUniform(String name, int length)
    {
        float[] result = new float[length];
        glGetUniformfv(programHandle, uniforms.get(name), result);
        return result;
    }

    public void updateUniform4f(String name, Vector3f v)
    {
        glUniform4f(uniforms.get(name), v.getX(), v.getY(), v.getZ(), 1);
    }

    public void updateUniformMatrix4f(String name, Matrix4f v)
    {
        glUniformMatrix4fv(uniforms.get(name), true, v.getMatrix().toArray());
    }

    public int getAttribLocation(String s)
    {
        return glGetAttribLocation(programHandle, s);
    }
}
