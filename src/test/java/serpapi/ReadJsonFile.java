package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Path;
import static java.nio.file.Files.readAllBytes;

/**
 * Fixture to read a JSON file as String
 */
public class ReadJsonFile
{
  public static String readAsString(Path path)
  {
    try
    {
      return new String(readAllBytes(path));
    }
    catch(IOException e)
    {
      e.printStackTrace();
      return "fail reading: " + path.toAbsolutePath();
    }
  }

  public static JsonObject readAsJson(Path path)
  {
    return new JsonParser()
        .parse(readAsString(path))
        .getAsJsonObject();
  }
}
