package dataAccess.DAO;
import Model.User;
import com.google.gson.Gson;
import dataAccess.DataAccessException;

import java.sql.*;
import java.util.HashSet;
import java.util.Collection;

import dataAccess.Database;

public class UserDao {
  static Collection<User> userInDB = new HashSet<User>();

  Database db = new Database();

  public void closeConnection(Connection conn) throws DataAccessException{
    try{
      db.closeConnection(conn);
    }catch(DataAccessException exception){
      throw new DataAccessException(exception.toString());
    }
  }
  /**
   * Inserts the user into the database based on their info
   *
   * @param insertUser the user provided to add into the database
   * */
  public void insertUser(User insertUser)throws DataAccessException{
    var connection = new Database().getConnection();

    User checkUser = findUser(insertUser.getUsername());
    if(checkUser != null){
      throw new DataAccessException("This username already exists in database");
    }
    String command = "INSERT INTO chess.Users(Username, Password, Email) VALUES(?,?,?)";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, insertUser.getUsername());
      pStatement.setString(2, insertUser.getPassword());
      pStatement.setString(3, insertUser.getEmail());
      pStatement.execute();
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }finally {
        closeConnection(connection);
    }
  }

  /**
   * RegisterService uses this function to see if a username is already taken
   * Uses find function to check
   * @param username the username to check
   * @return will return the username if it is not already taken
   * @throws DataAccessException
   */
  public String checkIfTaken(String username)throws DataAccessException{
    if(findUser(username) == null){
      return username;
    }else{
      return null;
    }
  }

  /**
   * Removes the user from the database
   *
   * @param username The string that represents a user's unique username
   * */
  public void removeUser(String username)throws DataAccessException{
    var connection = new Database().getConnection();
    if(findUser(username) == null){
      throw new DataAccessException("Username to remove is not in the database");
    }
    String command = "DELETE FROM chess.Users WHERE Username =?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, username);
      pStatement.execute();
    }catch (SQLException exception){
      throw new DataAccessException(exception.toString());
    }finally {
      closeConnection(connection);
    }

  }

  /**
   * Update's the user's info in the database
   *
   * @param username The string that represents a user's unique username
   * @param password The password as a string associated with the username
   * @param email The user's email as a string
   * */
  public void updateUser(String username, String password, String email) throws DataAccessException{
    var connection = new Database().getConnection();
    User UserToUpdate = findUser(username);
    if(UserToUpdate == null){
      throw new DataAccessException("Username to update is not in the database");
    }else{

      String command = "UPDATE chess.Users SET Password = ?, Email = ? WHERE Username = ?";
      try (PreparedStatement pStatement = connection.prepareStatement(command)) {
        pStatement.setString(1, password);
        pStatement.setString(2, email);
        pStatement.setString(3, username);
        pStatement.executeUpdate();
      }catch (SQLException exception){
        throw new DataAccessException(exception.toString());
      }finally {
        closeConnection(connection);
      }
    }
  }

  /**
   * Finds the user with the associated username
   *
   * @param username The string that represents a user's unique username
   * */
  public User findUser(String username) throws DataAccessException {
    var connection = new Database().getConnection();

    String command = "SELECT Password, Email FROM chess.Users WHERE Username=?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, username);
      ResultSet resultSet = pStatement.executeQuery();
      if(resultSet.next()){
        User foundUser = new User(username, resultSet.getString("Password"), resultSet.getString("Email")); //create the user with info in resultSet
        return foundUser;
      }
      else{
        return null;
      }
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }finally {
      closeConnection(connection);
    }
  }

  public Collection<User> findAllUsers() throws DataAccessException {
    var connection=new Database().getConnection();
    Gson gsonSerialize = new Gson();
    Collection<User> allUsers = new HashSet<User>();
    String command = "SELECT * FROM chess.Users";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      var getUser = pStatement.executeQuery();

      while(getUser.next()){
       User userToAdd = new User();
        userToAdd.setUsername(getUser.getString(1));
        userToAdd.setPassword(getUser.getString(2));
        userToAdd.setEmail(getUser.getString(3));
        allUsers.add(userToAdd);
      }
    }catch(SQLException e){
      throw new DataAccessException("Error occurred in findAll");
    }finally {
      closeConnection(connection);
    }
    return allUsers;
  }

  public void clearUsersInDatabase() throws DataAccessException {
    var connection= new Database().getConnection();
    String command="DELETE FROM chess.Users";
    try (PreparedStatement pStatement=connection.prepareStatement(command)) {
      pStatement.execute();
    } catch (SQLException exception) {
      throw new DataAccessException(exception.toString());
    } finally {
      closeConnection(connection);
    }
  }




}
