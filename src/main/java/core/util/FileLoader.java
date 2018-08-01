package core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class FileLoader
{
    public static String loadFileAsString(String path) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line = "";
        StringBuilder sb = new StringBuilder();

        while((line = reader.readLine()) != null)
        {
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String loadFile(String path, Consumer<String> consumer) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line = "";
        StringBuilder sb = new StringBuilder();

        while((line = reader.readLine()) != null)
        {
            sb.append(line);
            consumer.accept(line);
            sb.append("\n");
        }

        return sb.toString();
    }
}
