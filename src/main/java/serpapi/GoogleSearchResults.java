package serpapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class GoogleSearchResults
{
  // Set of constant
  public static final String SERP_API_KEY_NAME = "serp_api_key";
  public static String VERSION = "1.0.0";
  public static String BACKEND = "serpapi.com";

  // default static key
  public static String serp_api_key_default;

  // instance level key
  private String serp_api_key;

  // persist query parameter
  public Map<String,String> parameter;

  // initialize gson
  private static Gson gson = new Gson();

  // http request configuration
  private int httpConnectionTimeout;
  private int httpReadTimeout;

  /*
   * Constructor
   * @param parameter
   */
  public GoogleSearchResults(Map<String,String> parameter, String serp_api_key)
  {
    this.parameter = parameter;
    this.serp_api_key = serp_api_key;
  }

  /***
   *
   * @param parameter
   */
  public GoogleSearchResults(Map<String,String> parameter)
  {
    this.parameter = parameter;
  }

  public static String getSerpApiKey()
  {
    return serp_api_key_default;
  }

  /***
   * Build URL
   *
   * @return httpUrlConnection
   * @throws IOException
   */
  public HttpURLConnection buildConnection() throws GoogleSearchException
  {

    HttpURLConnection con;
    try
    {
      allowHTTPS();
      String query = ParameterStringBuilder.getParamsString(this.parameter);
      URL url = new URL("https://" + BACKEND + "/search?" + query);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
    }
    catch(IOException e)
    {
      throw new GoogleSearchException(e);
    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
      throw new GoogleSearchException(e);
    }
    catch(KeyManagementException e)
    {
      e.printStackTrace();
      throw new GoogleSearchException(e);
    }

    if(parameter.get("output") == "json")
    {
      con.setRequestProperty("Content-Type", "application/json");
    }

    //TODO Enable to set different timeout
    con.setConnectTimeout(getHttpConnectionTimeout());
    con.setReadTimeout(getHttpReadTimeout());

    con.setDoOutput(true);
    return con;
  }

  private void allowHTTPS() throws NoSuchAlgorithmException, KeyManagementException
  {
    TrustManager[] trustAllCerts;
    trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

        }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };
    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    /*
     * end of the fix
     */
  }

  public void buildParameter() throws GoogleSearchException
  {
    // Set current programming language
    this.parameter.put("source", "java");

    // Set serp_api_key
    if(this.parameter.get(SERP_API_KEY_NAME) == null)
    {
      if(this.serp_api_key != null)
      {
        this.parameter.put(SERP_API_KEY_NAME, this.serp_api_key);
      }
      else if(getSerpApiKey() != null)
      {
        this.parameter.put(SERP_API_KEY_NAME, getSerpApiKey());
      }
      else
      {
        // Let serp api undefined
        // throw new GoogleSearchException(SERP_API_KEY_NAME + " is not defined");
      }
    }
  }

  /***
   * Get results
   * @return
   */
  public String getResults() throws GoogleSearchException
  {
    buildParameter();
    HttpURLConnection con = this.buildConnection();

    BufferedReader in = null;
    try
    {
      InputStream is = con.getInputStream();
      Reader reader = new InputStreamReader(is);
      in = new BufferedReader(reader);
    }
    catch(IOException e)
    {
      throw new GoogleSearchException(e);
    }

    String inputLine;
    StringBuffer content = new StringBuffer();
    try
    {
      while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
      }
      in.close();
    }
    catch(IOException e)
    {
      throw new GoogleSearchException(e);
    }

    // Disconnect
    con.disconnect();
    
    return content.toString();
    //TODO Catch error 
    //TODO Read error message in the JSON ["error"]
  }

  /***
   * Get HTML output
   * @return String
   * @throws GoogleSearchException
   */
  public String getHtml() throws GoogleSearchException
  {
    this.parameter.put("output", "html");
    return this.getResults();
  }

  /***
   * Get JSON output
   * @return JsonObject parent node
   * @throws GoogleSearchException
   */
  public JsonObject getJson() throws GoogleSearchException
  {  
    this.parameter.put("output", "json");
    return asJson(getResults());
  }

  /***
   * Convert HTTP content to JsonValue
   * @param content
   * @return JsonObject
   */
  public JsonObject asJson(String content)
  {
    JsonElement element = gson.fromJson(content, JsonElement.class);
    return element.getAsJsonObject();
  }

  /***
   * Get JSON with images included
   * @return JsonObject parent node
   * @throws GoogleSearchException
   */
  public JsonObject getJsonWithImages() throws GoogleSearchException
  {  
    this.parameter.put("output", "json_with_images");
    return asJson(getResults());
  }

  public int getHttpConnectionTimeout()
  {
    return httpConnectionTimeout;
  }

  public void setHttpConnectionTimeout(int httpConnectionTimeout)
  {
    this.httpConnectionTimeout = httpConnectionTimeout;
  }

  public int getHttpReadTimeout()
  {
    return httpReadTimeout;
  }

  public void setHttpReadTimeout(int httpReadTimeout)
  {
    this.httpReadTimeout = httpReadTimeout;
  }
}