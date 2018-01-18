package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

  public static JsonObject readAsJson(Path path) throws FileNotFoundException
  {
    JsonReader reader = new JsonReader(new FileReader(path.toFile()));
    reader.setLenient(true);
    return new JsonParser()
        .parse(reader)
        .getAsJsonObject();
  }
}
