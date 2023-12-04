package Result;


public class LogoutResult {
  private String authToken;
  private String message;

  /**
   * Contains result data for the user's request to log out
   * @param message in case an error occurs
   */
  public LogoutResult(String message){}
  public LogoutResult(){}

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
