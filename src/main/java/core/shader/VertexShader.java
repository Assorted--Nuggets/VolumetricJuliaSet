package core.shader;
import static org.lwjgl.opengl.GL20.*;
public class VertexShader extends Shader
{
    public VertexShader(String shaderSource)
    {
        super(shaderSource);
    }

    @Override
    public int initShaderHandle()
    {
        return glCreateShader(GL_VERTEX_SHADER);
    }
}
