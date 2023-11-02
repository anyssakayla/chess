package Request;
import chess.Model.AuthToken;
import dataAccess.DAO.AuthDao;
import dataAccess.DataAccessException;

public class RegisterReq {
  private String username;
  private String password;
  private String email;
  private AuthToken authToken;
  //AuthDao authDao = new AuthDao(); //Created in case a request contains no authToken and needs to be assigned one


  /**
   * Contains request data for a user to register for a game login
   * @param username The string that represents a user's unique username
   * @param password The unique password that is associated with a user
   * @param email The email associated with a user
   */
  public RegisterReq(String username, String password, String email){
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public RegisterReq(){}
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

//  public AuthToken getAuthToken() throws DataAccessException {
//    if(authToken == null){
//      authToken = authDao.insertAuth(username);
//    }
//    return authToken;
//  }
//
//  public void setAuthToken(AuthToken authToken) {
//    this.authToken = authToken;
//  }
}
