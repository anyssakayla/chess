package dataAccess.DAO;
import chess.Model.User;
import dataAccess.DataAccessException;
import java.util.HashSet;
import java.util.Collection;
import java.util.UUID;
import dataAccess.DAO.UserDao;

public class UserDao {
  Collection<User> userInDB = new HashSet<User>();

  /**
   * Inserts the user into the database based on their info
   *
   * @param insertUser the user provided to add into the database
   * */
  public User insertUser(User insertUser)throws DataAccessException{
    for(User findUser : userInDB){
      if(findUser.getUsername().equals(insertUser)){ //check if the user i salready in the database
        throw new DataAccessException("User is already in the database");
      }
    }
    User addUser = new User(insertUser);
    userInDB.add(addUser);
    return addUser;

  }

  /**
   * Removes the user from the database
   *
   * @param username The string that represents a user's unique username
   * */
  public void removeUser(String username)throws DataAccessException{
    for (User findUser : userInDB){ //check if the username is in database first
      if(findUser.getUsername().equals(username)){ //check if a username is equal to the provided string username
        userInDB.remove(findUser); //if it is found, remove the user
      }
    }
    throw new DataAccessException("User is not in the database");
  }

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
  public User findUser(String username) throws DataAccessException{
    for (User findUser : userInDB){
      if(findUser.getUsername().equals(username)){ //check if a username is equal to the provided string username
        return findUser; //if it is found, return the user
      }
    }
    throw new DataAccessException("User is not in the database");
  }


}
