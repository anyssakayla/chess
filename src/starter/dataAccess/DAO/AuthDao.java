package dataAccess.DAO;
import dataAccess.Database;
import chess.Model.AuthToken;
import dataAccess.DataAccessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Collection;
import java.util.UUID;
import java.sql.DriverManager;

public class AuthDao {
  static UserDao currUser = new UserDao();
  public static Collection<AuthToken> authInDB = new HashSet<AuthToken>();
  public static AuthToken authToken;

  Database db = new Database();

  public void closeConnection(Connection conn) throws DataAccessException{
    try{
      db.closeConnection(conn);
    }catch(DataAccessException exception){
      throw new DataAccessException(exception.toString());
    }
  }

  public AuthDao() {
  }


  /**
   * Adds the provided authToken to the database
   *
   * @param authToken The token to be added
   * */
  public void insertAuth(AuthToken authToken) throws DataAccessException{
    var connection= new Database().getConnection();
    if(authToken == null){
      throw new DataAccessException("AuthToken to insert is null");
    }
    if(getAuth(authToken.getAuthToken()) != null){ //!= null because you have to input a new auth that is not already in database
      throw new DataAccessException("The AuthToken is already in the database");
    }
    String command = "INSERT INTO chess.AuthToken (Token, UserName) VALUES(?,?)";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, authToken.getAuthToken());
      pStatement.setString(2, authToken.getUsername());

      pStatement.execute();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
    finally {
      closeConnection(connection);
    }
  }

  /**
   * Removes the authToken associated with the provided username
   *
   * @param auth The string that represents a user's unique token
   * */
  public void removeAuth(String auth) throws DataAccessException{
    var connection= new Database().getConnection();
    if(getAuth(auth) == null){
      throw new DataAccessException("AuthToken not found in Database");
    }
    String command = "DELETE FROM chess.AuthToken WHERE Token =?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, auth);
      pStatement.execute();
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }finally {
      closeConnection(connection);
    }
  }

  public Collection<AuthToken> findAll() throws DataAccessException{
    var connection= new Database().getConnection();
    Collection<AuthToken> authTokensFound = new HashSet<AuthToken>();
    AuthToken authtok;
    ResultSet getTokens = null;
    String command = "SELECT * FROM chess.AuthToken";

    try (PreparedStatement pStatement = connection.prepareStatement(command)) {

      getTokens = pStatement.executeQuery();
      while (getTokens.next()) {
        authtok = new AuthToken(getTokens.getString("AuthToken"), getTokens.getString("Username"));
        authTokensFound.add(authtok);
      }
      return authTokensFound;
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authToken");
    } finally {
      if(getTokens != null) {
        try {
          getTokens.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    //return authInDB; //Delete? This was for the old findAll() without sql
  }

  /**
   * Finds and gets the authToken associated that is in the database
   * Checks that the authToken is in the database
   *
   * @param authToken The string that represents a user's unique token
   * */
  public AuthToken getAuth(String authToken) throws DataAccessException{
    var connection= new Database().getConnection();
    AuthToken authtok;
    ResultSet findToken = null;
    String command = "SELECT * FROM chess.AuthToken WHERE Token = ?;";

    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, authToken);
      findToken = pStatement.executeQuery();
      if (findToken.next()) {
        authtok = new AuthToken(findToken.getString("Token"), findToken.getString("Username"));
        return authtok;
      }
      else{
        return null;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authToken");
    } finally {
      if(findToken != null) {
        try {
          findToken.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
//    return null;
  }


  /**
   Gets the authToken associated with a username
   */

  public AuthToken getAuthTokenByUsername(String username) throws DataAccessException{
    var connection= new Database().getConnection();
    AuthToken authtok;
    ResultSet findToken = null;
    String command = "SELECT * FROM chess.AuthToken WHERE Username = ?;";

    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, username);
      findToken = pStatement.executeQuery();
      if (findToken.next()) {
        authtok = new AuthToken(findToken.getString("Token"), findToken.getString("Username"));
        return authtok;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authToken by username");
    } finally {
      if(findToken != null) {
        try {
          findToken.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return null;
  }


  public void clearAuthTokensInDB() throws DataAccessException{
    var connection= new Database().getConnection();
    String command = "DELETE FROM chess.AuthToken";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the authTokens in the database");
    }finally {
      closeConnection(connection);
    }
   // authInDB.clear();
  }

  public AuthToken getCurrToken()throws DataAccessException{
    return authToken;
  }


}
