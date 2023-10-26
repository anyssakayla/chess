package chess.Model;

public class AuthToken {
  String authToken;
  String username;

  /**
   * Clears all information from the database
   * @param authToken a unique token for a user
   * @param username The string that represents the user's authorization token
   *
   * */
 public AuthToken(String authToken, String username){
    this.authToken = authToken;
    this.username = username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
