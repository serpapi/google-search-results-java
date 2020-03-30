package serpapi;

/**
 * Wrapper any exception
 */
public class SerpApiClientException extends Exception {
  public SerpApiClientException() {
    super();
  }

  public SerpApiClientException(Exception e) {
    super(e);
  }

  public SerpApiClientException(String message) {
    super(message);
  }
}
