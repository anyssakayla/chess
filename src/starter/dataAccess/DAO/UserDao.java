package dataAccess.DAO;
import chess.Model.User;

public class UserDao {

  /**
   * Inserts the user into the database based on their info
   *
   * @param username The string that represents a user's unique username
   * @param password The password as a string associated with the username
   * @param email The user's email as a string
   * */
  public void insertUser(String username, String password, String email){}

  /**
   * Removes the user from the database
   *
   * @param username The string that represents a user's unique username
   * */
  public void removeUser(String username){}

  /**
   * Update's the user's info in the database
   *
   * @param username The string that represents a user's unique username
   * @param password The password as a string associated with the username
   * @param email The user's email as a string
   * */
  public void updateUser(String username, String password, String email){}

  /**
   * Finds the user with the associated username
   *
   * @param username The string that represents a user's unique username
   * */
  public User findUser(String username){return null;}
}
