import dataAccess.DataAccessException;
import dataAccess.Database;

import javax.xml.crypto.Data;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDB {
  Database database;

  public CreateDB(){
    this.database = new Database();
  }

  /**
   * Creates the database for chess
   * @throws DataAccessException
   */
  void createChessDB()throws DataAccessException{
    String command = "CREATE DATABASE IF NOT EXISTS chess";
    try(var connection = database.getConnection()){
      var pStatement = connection.prepareStatement(command);
      pStatement.executeUpdate();
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }
  }

  void createGameTable()throws DataAccessException{
    try(var connection = database.getConnection()){
      connection.setCatalog("chess");

      var gameTable = """
              CREATE TABLE IF NOT EXISTS Game (
              GameID INT NOT NULL AUTO_INCREMENT, 
              WhiteUsername VARCHAR(255),
              BlackUsername VARCHAR(255),
              GameName VARCHAR(255) NOT NULL,
              GameJSON longtext NOT NULL,
              PRIMARY KEY (GameID)
              )""";
      try(var pStatement = connection.prepareStatement(gameTable)){
        pStatement.executeUpdate();
      }catch(SQLException exception){
        throw new DataAccessException(exception.toString());
      }
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }
  }

  void createAuthTable()throws DataAccessException{
    try(var connection = database.getConnection()) {
      connection.setCatalog("chess");

      var authTable = """
              CREATE TABLE IF NOT EXISTS AuthToken ( 
              Token VARCHAR(255) NOT NULL,
              Username VARCHAR(255) NOT NULL,
              PRIMARY KEY (Token)
              )""";
      try(var pStatement = connection.prepareStatement(authTable)){
        pStatement.executeUpdate();
      }catch(SQLException exception){
        throw new DataAccessException(exception.toString());
      }
    }catch (SQLException exception){
      throw new DataAccessException(exception.toString());
    }
  }
  //observers connect username with gameID

  void createUserTable() throws DataAccessException{
    try(var connection = database.getConnection()) {
      connection.setCatalog("chess");

      var userTable = """
              CREATE TABLE IF NOT EXISTS Users (
              Username VARCHAR(255) NOT NULL,
              Password VARCHAR(255) NOT NULL,
              Email VARCHAR(255) NOT NULL,
              PRIMARY KEY (Username)
              )""";
      try(var pStatement = connection.prepareStatement(userTable)){
        pStatement.executeUpdate();
      }catch(SQLException exception){
        throw new DataAccessException(exception.toString());
      }
    }catch (SQLException exception){
      throw new DataAccessException(exception.toString());
    }
  }

  public void establishDatabase() throws DataAccessException{
    createChessDB();
    createGameTable();
    createAuthTable();
    createUserTable();
  }
}
