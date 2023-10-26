package dataAccess.DAO;
import chess.Model.AuthToken;
import dataAccess.DataAccessException;
import java.util.HashSet;
import java.util.Collection;
import java.util.UUID;

public class AuthDao {
  UserDao currUser;
  public static Collection<AuthToken> authInDB = new HashSet<AuthToken>();
  /**
   * Inserts an authtoken into the database depending on the provided username and AuthToken
   *
   * @param username The string that represents a user's unique username
   * */
  public AuthToken insertAuth(String username) throws DataAccessException{
    currUser.findUser(username);//see if user is in database
    for(AuthToken auth : authInDB){
      if(auth.getUsername().equals(username)){
        return auth; //return the authtoken if it is in database
      }
    }
    String uniqueToken = UUID.randomUUID().toString(); //create a unique string to be used for user's authToken
    AuthToken newToken = new AuthToken(uniqueToken, username); //create new authtoken associated with username
    authInDB.add(newToken); //add the new authToken to the database
    return newToken;
  }

  /**
   * Removes the authToken associated with the provided username
   *
   * @param auth The string that represents a user's unique token
   * */
  public void removeAuth(AuthToken auth) throws DataAccessException{
    authInDB.remove(getAuth(auth.getAuthToken())); //get the token and delete it from db collection
  }

  /**
   * Gets the authToken associated with the provided username
   *
   * @param authToken The string that represents a user's unique token
   * */
  public AuthToken getAuth(String authToken) throws DataAccessException{
    for(AuthToken auth : authInDB){ //search the collection for the indicated authToken string
      if(auth.getAuthToken().equals(authToken)){ //if it equals the authToken to find, return it
        return auth;
      }
    }
    throw new DataAccessException("The authToken is not in the database");
  }


}
