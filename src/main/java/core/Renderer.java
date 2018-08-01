package core;

import core.shader.BasicShaderProgram;
import core.util.*;
import math.Matrix4f;
import math.Transform4f;
import math.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.io.IOException;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class Renderer
{
    private static Renderer rendererInstance;
    public static Camera camera;
    public static BasicShaderProgram program;
    public static Loader loader;
    public static RawModel m;

    public static Renderer getInstance()
    {
        if(rendererInstance != null)
            return rendererInstance;
        rendererInstance = new Renderer();
        return rendererInstance;
    }

    public Renderer()
    {
        camera = new Camera();
    }

    private Matrix4f rot;
    private Matrix4f rotvel;
    private Matrix4f trans;
    public void init()
    {
        GL.createCapabilities();
        try
        {
            program = new BasicShaderProgram();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        program.link();
        program.init();
        program.use();


        glClearColor(0.1f, 0.0f, 0.1f, 0.0f);

        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glDisable(GL_CULL_FACE);
        glMatrixMode(GL_MODELVIEW);

        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        rot = new Matrix4f();
        rotvel = new Matrix4f();
        trans = new Matrix4f();

        Matrix4f.loadTranslation(trans, new Vector3f(0, 0, -1f));

        Matrix4f.loadRotation(rot, new Vector3f(0,0,0));
        Matrix4f.loadRotation(rotvel, new Vector3f(0, 1f, 0));

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };
        loader = new Loader();
        m = loader.loadToVAO(vertices, indices);
        //program.updateUniformMatrix4f("modelMatrix", Matrix4f.loadRotation(new Matrix4f(), new Vector3f(0, 45f, 0)));
    }
    private Matrix4f model2 = new Matrix4f();
    Transform4f transform = new Transform4f();

    public void render()
    {
        model2.getMatrix().loadIdentity();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        rot.getMatrix().mulInPlace(rotvel.getMatrix());

        program.update();
        program.updateUniformMatrix4f("modelMatrix", model2);


        GL30.glBindVertexArray(m.getVaoID());
        glEnableVertexAttribArray(0);

        //transform.setRotation(rot);
        for(float i = -2; i < 2; i += 0.005f)
        {
            transform.translate(new Vector3f(0, 0, i));
            transform.applyTransform(false);

            program.updateUniformMatrix4f("translation", transform.getTranslation());
            program.updateUniformMatrix4f("modelMatrix", transform.getTransformation());
            glDrawElements(GL_TRIANGLES, m.getVertexCount(), GL_UNSIGNED_INT, 0);
        }

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);


        Window.getInstance().swapBuffers();
        Window.getInstance().pollEvents();
    }
}
