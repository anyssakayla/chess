package Result;


public class LogoutResult {
  private String message;

  /**
   * Contains result data for the user's request to log out
   * @param message in case an error occurs
   */
  public LogoutResult(String message){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
