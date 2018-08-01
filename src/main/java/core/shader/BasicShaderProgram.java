package core.shader;

import core.util.FileLoader;
import core.Renderer;

import java.io.IOException;

public class BasicShaderProgram extends ShaderProgram
{

    public BasicShaderProgram() throws IOException
    {
        super(FileLoader.loadFileAsString("C:\\Users\\MD057682\\Documents\\shaders\\vertex.txt"),
              FileLoader.loadFileAsString("C:\\Users\\MD057682\\Documents\\shaders\\fragment.txt"));
    }

    @Override
    public void init()
    {
        addUniform("viewProjectionMatrix");
        addUniform("modelMatrix");
        addUniform("rotation");
        addUniform("translation");
        addUniform("juliapos");
    }

    @Override
    public void update()
    {
        updateUniformMatrix4f("viewProjectionMatrix", Renderer.camera.getProjectionView());
        updateUniform4f("juliapos", Renderer.camera.getPosition());
    }
}
