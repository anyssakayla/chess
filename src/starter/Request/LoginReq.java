package Request;


public class LoginReq {
  private String username;
  private String password;

  /**
   * Contains request data to log the user in
   * @param username The string that represents a user's unique username
   * @param password The unique password that is associated with a user to log into the game
   */
  public LoginReq(String username, String password){} //constructor

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
