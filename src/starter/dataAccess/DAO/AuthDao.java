package dataAccess.DAO;
import chess.Model.AuthToken;
import dataAccess.DataAccessException;
import java.util.HashSet;
import java.util.Collection;
import java.util.UUID;

public class AuthDao {
  static UserDao currUser = new UserDao();
  public static Collection<AuthToken> authInDB = new HashSet<AuthToken>();
  public static AuthToken authToken;
  /**
   * Inserts an authtoken into the database depending on the provided username and AuthToken
   * Creates an authtoken for the username
   *
   * @param username The string that represents a user's unique username
   * */
  public AuthToken insertAuth(String username) throws DataAccessException{
    ;//see if user is in database
    if(currUser.findUser(username) == null){
      throw new DataAccessException("User not in database");
    }
        String uniqueToken=UUID.randomUUID().toString(); //create a unique string to be used for user's authToken
        AuthToken newToken=new AuthToken(uniqueToken, username); //create new authtoken associated with username
          authInDB.add(newToken); //add the new authToken to the database
          return newToken;

  }

  /**
   * Adds the provided authToken to the database
   *
   * @param authToken The token to be added
   * */
  public void insertAuth(AuthToken authToken) throws DataAccessException{
    if(!authInDB.contains(authToken)){
      authInDB.add(authToken);
    }
  }

  /**
   * Removes the authToken associated with the provided username
   *
   * @param auth The string that represents a user's unique token
   * */
  public void removeAuth(String auth) throws DataAccessException{
    authInDB.remove(getAuth(auth)); //get the token and delete it from db collection
  }

  public Collection<AuthToken> findAll(){
    return authInDB;
  }

  /**
   * Gets the authToken associated that is in the database
   * Checks that the authToken is in the database
   *
   * @param authToken The string that represents a user's unique token
   * */
  public AuthToken getAuth(String authToken){
    for(AuthToken auth : authInDB){ //search the collection for the indicated authToken string
      if(auth.getAuthToken().equals(authToken)){ //if it equals the authToken to find, return it
        return auth;
      }
    }
   // System.out.println("The authToken is not in the database");
    return null;
  }
  public String getAuthStringByUsername(String username) throws DataAccessException{
    for(AuthToken auth : authInDB){ //search the collection for the indicated authToken string
      if(auth.getUsername().equals(username)){ //if it equals the authToken to find, return it
        if(!authInDB.contains(auth.getAuthToken())){ //check that the authtoken isn't already in the db
         // authInDB.add(auth);
          return auth.getAuthToken(); //return the authtoken if it is in database
        }
        else{
          String uniqueToken = UUID.randomUUID().toString(); //create a unique string to be used for user's authToken
          AuthToken newToken = new AuthToken(uniqueToken, username); //create new authtoken associated with username
          if(!authInDB.contains(newToken)){
            authInDB.add(newToken); //add the new authToken to the database
            return newToken.getAuthToken();
          }
        }
        //insertAuth(auth.getAuthToken()); //this doesn't change the auth if it is duplicate
        //return auth.getAuthToken();
      }
    }
    return null;
  }

  public AuthToken getAuthTokenByUsername(String username) throws DataAccessException{
    for(AuthToken auth : authInDB){ //search the collection for the indicated authToken string
      if(auth.getUsername().equals(username)){ //if it equals the authToken to find, return it
        return auth;
      }
    }
    throw new DataAccessException("The user has no authToken");
  }

  public void clearAuthTokensInDB(){
    authInDB.clear();
  }

  public AuthToken getCurrToken()throws DataAccessException{
    return authToken;
  }


}
