package core;

public class CoreEngine
{
    private Window m_window;
    private Renderer m_renderer;

    private boolean m_isRunning = false;

    public CoreEngine()
    {
        m_window = Window.getInstance();
        m_renderer = Renderer.getInstance();
    }

    public void start()
    {
        m_isRunning = true;
        m_window.createWindow("Test", 1920, 1080);
        m_renderer.init();
        loop();
    }

    public void loop()
    {
        while(m_isRunning)
        {
            if(m_window.shouldClose())
            {
                m_isRunning = false;
            }
            m_renderer.render();
        }
    }

    public void stop()
    {
        m_window.destroy();
    }
}
