package core;

import math.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private static Window windowInstance;

    private String m_name;
    private long m_windowHandle;

    private int m_width;
    private int m_height;

    public static Window getInstance()
    {
        if(windowInstance != null)
        {
            return windowInstance;
        }
        windowInstance = new Window();
        return windowInstance;
    }

    public void createWindow(String name, int width, int height)
    {
        this.m_width = width;
        this.m_height = height;
        this.m_name = name;

        if(!glfwInit())
            throw new IllegalStateException("Unable to Initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        m_windowHandle = glfwCreateWindow(m_width, m_height, m_name, NULL, NULL);

        if(m_windowHandle == NULL)
            throw new RuntimeException("Failed to create window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(m_windowHandle, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(m_windowHandle,
                             (vidmode.width() - pWidth.get(0)) / 2,
                             (vidmode.height() - pHeight.get(0)) / 2);
        }
        glfwSetKeyCallback(m_windowHandle, (long window, int key, int scancode, int action, int mods) ->{
            switch(key)
            {
                case GLFW_KEY_W:
                    Renderer.camera.move(new Vector3f(0, 0, 0.05f));
                    break;
                case GLFW_KEY_S:
                    Renderer.camera.move(new Vector3f(0, 0, -0.05f));
                    break;
                case GLFW_KEY_A:
                    Renderer.camera.move(new Vector3f(0.05f, 0, 0));
                    break;
                case GLFW_KEY_D:
                    Renderer.camera.move(new Vector3f(-0.05f, 0, 0));
                    break;
                case GLFW_KEY_SPACE:
                    Renderer.camera.move(new Vector3f(0, 0.05f, 0));
                    break;
                case GLFW_KEY_LEFT_CONTROL:
                    Renderer.camera.move(new Vector3f(0, -0.05f, 0));
                    break;
            }

            if(action == GLFW_RELEASE)
            {
                Renderer.camera.move(Vector3f.ORIGIN);
            }

        });

        glfwSetCursorPosCallback(m_windowHandle, (long window, double xpos, double ypos) -> {

            Renderer.camera.rotate(new Vector3f(-(((float)ypos/1080)-1f)*180f, -(((float)xpos/1920)-1f)*180f,0));
            //System.out.println(xpos + " " + ypos);
            //Renderer.camera.rotate(new Vector3f((float)(-ypos/800)*180, (float)(xpos/800)*360, 0));
        });
        glfwSetInputMode(m_windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwMakeContextCurrent(m_windowHandle);
        glfwSwapInterval(1);
        glfwShowWindow(m_windowHandle);
    }

    public void destroy()
    {
        glfwFreeCallbacks(m_windowHandle);
        glfwDestroyWindow(m_windowHandle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public boolean shouldClose()
    {
        return glfwWindowShouldClose(m_windowHandle);
    }

    public void swapBuffers()
    {
        glfwSwapBuffers(m_windowHandle);
    }

    public void pollEvents()
    {
        glfwPollEvents();
    }

    public String getName()
    {
        return m_name;
    }

    public void setName(String m_name)
    {
        this.m_name = m_name;
    }

    public long getWindowHandle()
    {
        return m_windowHandle;
    }

    public void setWindowHandle(long m_windowHandle)
    {
        this.m_windowHandle = m_windowHandle;
    }

    public int getWidth()
    {
        return m_width;
    }

    public void setWidth(int m_width)
    {
        this.m_width = m_width;
    }

    public int getHeight()
    {
        return m_height;
    }

    public void setHeight(int m_height)
    {
        this.m_height = m_height;
    }
}
