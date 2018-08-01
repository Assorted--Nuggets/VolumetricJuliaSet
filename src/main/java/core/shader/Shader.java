package core.shader;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader
{
    private int shaderHandle;

    public Shader(String shaderSource)
    {
        System.out.println(shaderSource);
        shaderHandle = initShaderHandle();
        if (shaderHandle == 0)
        {
            throw new RuntimeException("Unable to create vertex shader");
        }
        glShaderSource(shaderHandle, shaderSource);
        glCompileShader(shaderHandle);
        if (glGetShaderi(shaderHandle, GL_COMPILE_STATUS) == 0)
        {
            System.out.println(glGetShaderInfoLog(shaderHandle, 1024));
            throw new RuntimeException("Failed to compile shader");
        }
    }

    public abstract int initShaderHandle();
    public int getShaderHandle()
    {
        return this.shaderHandle;
    }
}
