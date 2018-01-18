package serpapi;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * HTTPS client for Serp API
 */
public class GoogleSearchResultsClient
{
  // http request configuration
  private int httpConnectionTimeout;
  private int httpReadTimeout;

  public static String VERSION = "1.0.0";
  public static String BACKEND = "serpapi.com";

  /***
   * Build URL
   *
   * @return httpUrlConnection
   * @throws IOException
   */
  public HttpURLConnection buildConnection(Map<String, String> parameter) throws GoogleSearchException
  {
    HttpURLConnection con;
    try
    {
      allowHTTPS();
      String query = ParameterStringBuilder.getParamsString(parameter);
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

    if(parameter.get("output") == null)
    {
      throw new GoogleSearchException("output format must be defined");
    }

    if(parameter.get("output").startsWith("json"))
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

  /***
   * Get results
   *
   * @param parameter
   * @return http response body
   */
  public String getResults(Map<String, String> parameter) throws GoogleSearchException
  {
    HttpURLConnection con = buildConnection(parameter);

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

