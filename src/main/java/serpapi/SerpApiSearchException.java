package serpapi;

/**
 * SerpApi Search Exception wraps any exception related to the connection with SerpApi.com
 */
public class SerpApiSearchException extends Exception {
  /**
   * Constructor
   */
  public SerpApiSearchException() {
    super();
  }

  /**
   * Constructor
   * @param exception original exception
   */
  public SerpApiSearchException(Exception exception) {
    super(exception);
  }

  /**
   * Constructor
   * @param message short description of the root cause 
   */
  public SerpApiSearchException(String message) {
    super(message);
  }
}
