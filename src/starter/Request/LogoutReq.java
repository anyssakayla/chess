package Request;

public class LogoutReq {
  private String username;
  private String authToken;

  /**
   * Contains request data for the user to log out
   * @param username The string that represents a user's unique username
   * @param authToken The unique token that is associated with a user
   */
  public LogoutReq(String username, String authToken){
    this.authToken = authToken;
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
