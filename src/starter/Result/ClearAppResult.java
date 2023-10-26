package Result;

public class ClearAppResult {
  private String message;

  /**
   * The result data from a user's request to clear the application
   * @param message A potential error message
   */
  public ClearAppResult(String message){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
