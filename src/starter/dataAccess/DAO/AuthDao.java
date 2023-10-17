package dataAccess.DAO;
import chess.Model.AuthToken;

public class AuthDao {

  /**
   * Inserts an authtoken into the database depending on the provided username and AuthToken
   *
   * @param username The string that represents a user's unique username
   * @param authToken The string that represents the user's authorization token
   * */
  public void insertAuth(String username, String authToken){

  }

  /**
   * Removes the authToken associated with the provided username
   *
   * @param username The string that represents a user's unique username
   * */
  public void removeAuth(String username){}

  /**
   * Gets the authToken associated with the provided username
   *
   * @param username The string that represents a user's unique username
   * */
  public void getAuth(String username){}
}
