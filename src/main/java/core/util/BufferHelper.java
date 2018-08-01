package core.util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferHelper
{
    public static FloatBuffer storeToFloatBuffer(Float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        for(float i : data)
        {
            buffer.put(i);
        }
        buffer.flip();
        return buffer;
    }

    public static IntBuffer storeToIntBuffer(Integer[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        for(int i : data)
        {
            buffer.put(i);
        }
        buffer.flip();
        return buffer;
    }
}
