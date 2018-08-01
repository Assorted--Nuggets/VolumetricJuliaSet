package core.shader;

import static org.lwjgl.opengl.GL20.*;

public class FragmentShader extends Shader
{
    public FragmentShader(String shaderSource)
    {
        super(shaderSource);
    }

    @Override
    public int initShaderHandle()
    {
        return glCreateShader(GL_FRAGMENT_SHADER);
    }
}
