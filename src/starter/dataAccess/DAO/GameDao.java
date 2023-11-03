package dataAccess.DAO;
import static java.lang.Math.random;
import dataAccess.DataAccessException;
import chess.Model.Game;
import chess.ChessGame;
import chess.ChessGameImpl;
import java.util.Collection;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;


public class GameDao {
  public static Collection<Game> gamesInDB = new HashSet<Game>();
  /**
   * Inserts a new game into the database with the provided information
   *
   * @param gameName Represents the name of the new game to be added to the database
   * */
  public Game insertGame(String gameName) throws DataAccessException{
    int gameID = createGameID();
    Game gameToInsert = new Game(gameID, gameName);
    gamesInDB.add(gameToInsert);
    return gameToInsert;
  }

  /**
   * Finds the game associated with the provided gameID
   *
   * @param gameID Represents a unique ID number that is associated to a game
   * @return The ChessGame that is associated with the gameID
   * */
  public Game findGame(int gameID) throws DataAccessException{
    for(Game findGame : gamesInDB){
      if(findGame.getGameID() == gameID){
        return findGame;
      }
    }
    return null;
  }

  public Game findGameName(String gameName){
    for(Game game : gamesInDB){
      if(game.getGameName() == gameName){
        return game;
      }
    }
    return null;
  }

  /**
   * Returns all games in database
   *
   * @return A list of strings representing all the game names in the database
   * */
  public Collection<Game> findAll(){
    return gamesInDB;
  }

  /**
   * Allows the user to claim a spot in the game and stores the user's team as white or black
   *
   * @param username The string that represents a user's unique username
   * @param gameID the unique ID for the game the username wants to join
   * @param teamColor the team color the user is on; black or white
   * */
  public void claimSpot(String username, int gameID, ChessGame.TeamColor teamColor) throws DataAccessException {
    Game existingGame = findGame(gameID); //make sure the user is entering into a game that is in database
    if(existingGame == null){ //might have to delete this
      throw new DataAccessException("Username is null");
    }
    if(teamColor == ChessGame.TeamColor.BLACK){
      existingGame.setBlackUsername(username);
    }
    else if(teamColor == ChessGame.TeamColor.WHITE){
      existingGame.setWhiteUsername(username);
    }
    else if(teamColor == null){
      existingGame.addGameObserver(username); //user is an observer if they don't specify a team color
    }
  }

  /**
   * Updates a chess game in the database to current game status
   *
   * @param currentGame The game in which these updates should be saved to
   * */
  public void updateGame(Game currentGame){
    currentGame.setGame(currentGame.getGame()); //TODO: what else do we add?
  }

  /**
   * Removes the game from the database
   *
   * @param gameID The gameID for the game to be removed
   * */
  public void removeGame(int gameID) throws DataAccessException{
    Game gameToRemove = findGame(gameID); //checks if it is in db
    gamesInDB.remove(gameToRemove); //if it finds the game to be removed, remove it
  }

  /**
   * Clears all data from the database
   * */
  public void clearGames(){
    gamesInDB.clear();
  } //clears all game data from database

  public int createGameID(){ //TODO: FIX THIS FUNCTION
//    Random random = new Random();
//    int createID = random.nextInt(1000); //random int with the 1000 bound
//    while (gamesInDB.contains(createID)){ //if it is already in database, make a new one
//      createID = random.nextInt(1000);
//    }
//    return createID;

    int createID = (int)(random() * 1000);
    for(Game gameID : gamesInDB){
      if(gameID.getGameID() == createID){
        createGameID(); //if it already exists, recursively call createGameID until it is a unique ID
      }
    }
    return createID;
  }

  public int getGameID(String gameName) throws DataAccessException{
    for(Game game : gamesInDB){
      if(game.getGameName().equals(gameName)){
        return game.getGameID();
      }
    }
    throw new DataAccessException("The gameName is not in the database");
  }
}
