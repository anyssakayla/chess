package dataAccess.DAO;
import dataAccess.DataAccessException;
import chess.Model.Game;
import chess.ChessGame;
import chess.ChessGameImpl;

import java.util.ArrayList;


public class GameDao {

  /**
   * Inserts a new game into the database with the provided information
   *
   * @param newGame Represents the new game to be added to the database
   * */
  public void insertGame(Game newGame) throws DataAccessException{

  }

  /**
   * Finds the game associated with the provided gameID
   *
   * @param gameID Represents a unique ID number that is associated to a game
   * @return The ChessGame that is associated with the gameID
   * */
  public String findGame(int gameID){return null;}

  /**
   * Returns all games in database
   *
   * @return A list of strings representing all the game names in the database
   * */
  public ArrayList<String> findAll(){return null;}

  /**
   * Allows the user to claim a spot in the game and stores the user's team as white or black
   *
   * @param username The string that represents a user's unique username
   * */
  public void claimSpot(String username){}

  /**
   * Updates a chess game in the database to current game status
   *
   * @param currentGame The game in which these updates should be saved to
   * */
  public void updateGame(Game currentGame){}

  /**
   * Removes the game from the database
   *
   * @param game The game to be removed
   * */
  public void removeGame(Game game){}

  /**
   * Clears all data from the database
   * */
  public void clearGames(){} //clears all game data from database

}
