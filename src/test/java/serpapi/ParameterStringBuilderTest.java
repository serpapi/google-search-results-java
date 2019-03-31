package serpapi;

import org.junit.Test;
import serpapi.ParameterStringBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/***
 * Test converting map to URL query
 */
public class ParameterStringBuilderTest
{
  @Test
  public void getParamsString() throws Exception
  {
    Map<String, String> map = new HashMap<String, String>();
    map.put("output", "json");
    map.put("key", "value");

    assertEquals(ParameterStringBuilder.getParamsString(map),
        "output=json&key=value");
  }

}