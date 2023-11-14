package dataAccess.DAO;
import static java.lang.Math.random;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import com.google.gson.*;
import dataAccess.DataAccessException;
import chess.Model.Game;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.Database;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;
import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class GameDao {
  public static Collection<Game> gamesInDB = new HashSet<Game>();

  Database db = new Database();

  public void closeConnection(Connection conn) throws DataAccessException{
    try{
      db.closeConnection(conn);
    }catch(DataAccessException exception){
      throw new DataAccessException(exception.toString());
    }
  }

  /**
   * Inserts a new game into the database with the provided information
   *
   * @param gameToInsert Represents the new game to be added to the database
   * */
  public int insertGame(Game gameToInsert) throws DataAccessException{
    var connection= new Database().getConnection();
   // Game gameToInsert = new Game(getGameID(gameName), gameName); //Creates a game object
    if(gameToInsert.getGameName() == null){
      throw new DataAccessException("Game name to enter is null");
    }

    String command = "INSERT INTO chess.Game (WhiteUsername, BlackUsername, GameName, GameJSON) VALUES(?,?,?,?)";
    try (PreparedStatement pStatement = connection.prepareStatement(command, RETURN_GENERATED_KEYS)) {
      pStatement.setString(1, gameToInsert.getWhiteUsername());
      pStatement.setString(2, gameToInsert.getBlackUsername());
      pStatement.setString(3, gameToInsert.getGameName());
      pStatement.setString(4, new Gson().toJson(gameToInsert.getGame()));
      pStatement.executeUpdate();

      var generatedKey = pStatement.getGeneratedKeys();
      var gameID = 0;
      generatedKey.next();
      gameID = generatedKey.getInt(1);

      return gameID;
    }    catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authToken");
    }finally {
      closeConnection(connection);
    }
  }

  /**
   * Finds the game associated with the provided gameID
   *
   * @param gameID Represents a unique ID number that is associated to a game
   * @return The ChessGame that is associated with the gameID
   * */
  public Game findGame(int gameID) throws DataAccessException{ //TODO: FIX
    var connection= new Database().getConnection();
    Game gameToFind = new Game();
    Gson gsonSerialize = new Gson();
    String command = "SELECT WhiteUsername, BlackUsername, GameName, GameJSON FROM chess.Game WHERE GameID=?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setInt(1, gameID);
      ResultSet resultSet = pStatement.executeQuery();

      if(resultSet.next()){
        String gameName = resultSet.getString("GameName");
        String whiteUser = resultSet.getString("WhiteUsername");
        String blackUser = resultSet.getString("BlackUsername");

        String Json = resultSet.getString("GameJSON");
        ChessBoardImpl gameBoard = gsonSerialize.fromJson(Json, ChessBoardImpl.class);
        ChessGameImpl gameImpl = new ChessGameImpl();
        gameImpl.setBoard(gameBoard);
        Game gameToReturn = new Game(gameID, whiteUser, blackUser, gameName, gameImpl);
        return gameToReturn;

      }else{
        return null;
      }
    }catch(SQLException e){
      e.printStackTrace();
      throw new DataAccessException("Error occurred while finding game from gameID");
    }finally {
      closeConnection(connection);
    }
  }


  public Game findGameName(String gameName) throws DataAccessException {
    var connection= new Database().getConnection();
    Gson gsonSerialize = new Gson();
    String command = "SELECT GameID, WhiteUsername, BlackUsername, GameJSON FROM chess.Game WHERE GameName=?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, gameName);
      ResultSet resultSet = pStatement.executeQuery();

      if(resultSet.next()){

        int gameID = resultSet.getInt("GameID");
        String whiteUsername = resultSet.getString("WhiteUsername");
        String blackUsername = resultSet.getString("BlackUsername");
        String Json = resultSet.getString("GameJSON");
        ChessBoardImpl gameBoard = gsonSerialize.fromJson(Json, ChessBoardImpl.class);
        ChessGameImpl gameImpl = new ChessGameImpl();
        gameImpl.setBoard(gameBoard);
        return new Game(gameID, whiteUsername, blackUsername, gameName, gameImpl);

      }else{
        return null;
      }
    }catch(SQLException e){
      throw new DataAccessException("Error occurred while finding game from gameID");
    }finally {
      closeConnection(connection);
    }
  }
  /**
   * Returns all games in database
   *
   * @return A list of strings representing all the game names in the database
   * */
  public Collection<Game> findAll() throws DataAccessException {
    var connection= new Database().getConnection();
    Gson gsonSerialize = new Gson();
    Collection<Game> allGames = new HashSet<Game>();
    String command = "SELECT * FROM chess.Game";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      var getGame = pStatement.executeQuery();

      while(getGame.next()){
        Game gameToAdd = new Game(getGame.getInt(1), getGame.getString(4));
       gameToAdd.setWhiteUsername(getGame.getString(2));
        gameToAdd.setBlackUsername(getGame.getString(3));
        String Json = getGame.getString("GameJSON");
        ChessBoardImpl gameBoard = gsonSerialize.fromJson(Json, ChessBoardImpl.class);
        ChessGameImpl gameImpl = new ChessGameImpl();
        gameImpl.setBoard(gameBoard);
        gameToAdd.setGame(gameImpl);
        allGames.add(gameToAdd);
      }
    }catch(SQLException e){
      throw new DataAccessException("Error occured in findAll");
    }finally {
      closeConnection(connection);
    }
    return allGames;
  }

  /**
   * Allows the user to claim a spot in the game and stores the user's team as white or black
   *
   * @param username The string that represents a user's unique username
   * @param gameID the unique ID for the game the username wants to join
   * @param teamColor the team color the user is on; black or white
   * */
  public void claimSpot(String username, int gameID, ChessGame.TeamColor teamColor) throws DataAccessException {
    var connection= new Database().getConnection();
    Game existingGame = findGame(gameID); //make sure the user is entering into a game that is in database

    if(teamColor == ChessGame.TeamColor.BLACK ){
      if(existingGame.getBlackUsername() == null){
        existingGame.setBlackUsername(username);
      }else{
        throw new DataAccessException("Spot already taken");
      }
    }

    else if(teamColor == ChessGame.TeamColor.WHITE){
      if(existingGame.getWhiteUsername() == null){
        existingGame.setWhiteUsername(username);
      }else{
        throw new DataAccessException("Spot already taken");
      }
    }
    else if(teamColor == null){
      return;
    }
    String command = "UPDATE chess.Game SET WhiteUsername=?, BlackUsername=? WHERE GameID=?";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, existingGame.getWhiteUsername());
      pStatement.setString(2, existingGame.getBlackUsername());
      pStatement.setInt(3, gameID);
    //  pStatement.setString(3, existingGame.getGameName());
     // pStatement.setString(4, new Gson().toJson(existingGame.getGame()));
      pStatement.executeUpdate();
    }    catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authToken");
    } finally {
      closeConnection(connection);
    }
  }

  /**
   * Updates a chess game in the database to current game status
   *
   * @param currentGame The game in which these updates should be saved to
   * */
  public void updateGame(Game currentGame) throws DataAccessException {
    var connection= new Database().getConnection();
    Game existingGame = findGame(currentGame.getGameID());
    if(existingGame == null){
      throw new DataAccessException("Error: You can't update a game that isn't in the database");
    }

    String command = "UPDATE chess.Game SET WhiteUsername = ?, BlackUsername = ?, GameName = ?, GameGson = ? WHERE GameID = ? ";
    try (PreparedStatement pStatement = connection.prepareStatement(command)) {
      pStatement.setString(1, currentGame.getWhiteUsername());
      pStatement.setString(2, currentGame.getBlackUsername());
      pStatement.setString(3, currentGame.getGameName());
      pStatement.setString(4, new Gson().toJson(currentGame.getGame()));
      pStatement.setInt(5, currentGame.getGameID());
      pStatement.execute();
    }catch(SQLException e){
      throw new DataAccessException("Error occurred in update game");
    }finally {
      closeConnection(connection);
    }
  }

  /**
   * Removes the game from the database
   *
   * @param gameID The gameID for the game to be removed
   * */
  public void removeGame(int gameID) throws DataAccessException{
    var connection= new Database().getConnection();

    if(findGame(gameID) == null){
      throw new DataAccessException("GameID to remove is not in Database");
    }
    String command = "DELETE FROM chess.Game WHERE GameID = ?";
    try(var pStatement = connection.prepareStatement(command)){
      pStatement.setInt(1, gameID);
      pStatement.execute();
    }catch (SQLException exception){
      throw new DataAccessException(exception.toString());
    }finally {
      closeConnection(connection);
    }
  }

  /**
   * Clears all data from the database
   * */
  public void clearGames() throws DataAccessException{
    var connection= new Database().getConnection();

    String command = "DELETE FROM chess.Game";
    try(var pStatement = connection.prepareStatement(command)){
      pStatement.execute();
    }catch(SQLException exception){
      throw new DataAccessException(exception.toString());
    }
    finally {
      closeConnection(connection);
    }
  } //clears all game data from database


  public static class ChessAdapter implements JsonDeserializer<ChessGame>{
    public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException{
      return new Gson().fromJson(el, ChessGame.class);
    }
    public static Gson serializer(){
      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(Game.class, new ChessAdapter());
      return builder.create();
    }
  }
}

