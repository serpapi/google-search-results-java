package serpapi;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * HTTPS search for Serp API
 */
public class SerpApiHttpClient {
  // http request configuration
  private int httpConnectionTimeout;
  private int httpReadTimeout;

  public static String VERSION = "2.0.1";
  public static String BACKEND = "https://serpapi.com";

  // initialize gson
  private static Gson gson = new Gson();

  // path
  public String path;

  /***
   * @param String path
   */
  public SerpApiHttpClient(String path) {
    this.path = path;
  }

  /***
   * Build URL
   *
   * @param path  url end point
   * @param param hash
   * @return httpUrlConnection
   * @throws IOException
   */
  public HttpURLConnection buildConnection(String path, Map<String, String> parameter) throws SerpApiSearchException {
    HttpURLConnection con;
    try {
      allowHTTPS();
      String query = ParameterStringBuilder.getParamsString(parameter);
      URL url = new URL(BACKEND + path + "?" + query);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
    } catch (IOException e) {
      throw new SerpApiSearchException(e);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new SerpApiSearchException(e);
    } catch (KeyManagementException e) {
      e.printStackTrace();
      throw new SerpApiSearchException(e);
    }

    String outputFormat = parameter.get("output");
    if (outputFormat == null) {
      if (path.startsWith("/search?")) {
        throw new SerpApiSearchException("output format must be defined: " + path);
      }
    } else if (outputFormat.startsWith("json")) {
      con.setRequestProperty("Content-Type", "application/json");
    }

    // TODO Enable to set different timeout
    con.setConnectTimeout(getHttpConnectionTimeout());
    con.setReadTimeout(getHttpReadTimeout());

    con.setDoOutput(true);
    return con;
  }

  private void allowHTTPS() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts;
    trustAllCerts = new TrustManager[] { new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }

    } };

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

  /***
   * Get results
   *
   * @param parameter
   * @return http response body
   */
  public String getResults(Map<String, String> parameter) throws SerpApiSearchException {
    HttpURLConnection con = buildConnection(this.path, parameter);

    // Get HTTP status
    int statusCode = -1;
    // Hold response stream
    InputStream is = null;
    // Read buffer
    BufferedReader in = null;
    try {
      statusCode = con.getResponseCode();

      if (statusCode == 200) {
        is = con.getInputStream();
      } else {
        is = con.getErrorStream();
      }

      Reader reader = new InputStreamReader(is);
      in = new BufferedReader(reader);
    } catch (IOException e) {
      throw new SerpApiSearchException(e);
    }

    String inputLine;
    StringBuffer content = new StringBuffer();
    try {
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
    } catch (IOException e) {
      throw new SerpApiSearchException(e);
    }

    // Disconnect
    con.disconnect();

    if (statusCode != 200) {
      triggerSerpApiClientException(content.toString());
    }
    return content.toString();
  }

  public void triggerSerpApiClientException(String content) throws SerpApiSearchException {
    String errorMessage;
    try {
      JsonObject element = gson.fromJson(content, JsonObject.class);
      errorMessage = element.get("error").getAsString();
    } catch (Exception e) {
      throw new AssertionError("invalid response format: " + content);
    }
    throw new SerpApiSearchException(errorMessage);
  }

  public int getHttpConnectionTimeout() {
    return httpConnectionTimeout;
  }

  public void setHttpConnectionTimeout(int httpConnectionTimeout) {
    this.httpConnectionTimeout = httpConnectionTimeout;
  }

  public int getHttpReadTimeout() {
    return httpReadTimeout;
  }

  public void setHttpReadTimeout(int httpReadTimeout) {
    this.httpReadTimeout = httpReadTimeout;
  }

}
