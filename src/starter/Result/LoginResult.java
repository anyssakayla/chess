package Result;

public class LoginResult {
  private String message;
  private String authToken;
  private String username;

  /**
   * The result data to log the user in
   * @param username The string that represents a user's unique username
   * @param authToken The unique token associated with a user
   * @param message in case an error occurs, it will use a message
   */
  public LoginResult(String message, String authToken, String username){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public boolean isSuccess() { return true; //TODO: FIX THIS
  }
}
