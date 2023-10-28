package Request;

public class RegisterReq {
  private String username;
  private String password;
  private String email;

  /**
   * Contains request data for a user to register for a game login
   * @param username The string that represents a user's unique username
   * @param password The unique password that is associated with a user
   * @param email The email associated with a user
   */
  public RegisterReq(String username, String password, String email){}
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
    this.password=password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
