package chess.Model;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DAO.GameDao;

public class Game {
  int gameID;
  String whiteUsername;
  String blackUsername;
  String gameName;
  ChessGame game = new ChessGameImpl();
  private Collection<String> gameObserver = new HashSet<String>();

  /**
   * Represents a ChessGame ID
   *
   * @param gameID Represents a unique ID number that is associated to a game
   * @param gameName unique name that represents a chess game
   * */
  public Game(int gameID, String gameName){
    this.gameID = gameID;
    this.gameName = gameName;
  }

  public Game(String gameName){
    this.gameName = gameName;
    GameDao gameDao = new GameDao();
    gameID = gameDao.findAll().size() + 1000;
    game = new ChessGameImpl();
  }
  public Game() {}

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID = gameID;
  }

  public String getWhiteUsername() {
    return whiteUsername;
  }

  public void setWhiteUsername(String whiteUsername) {
    this.whiteUsername=whiteUsername;
  }

  public String getBlackUsername() {
    return blackUsername;
  }

  public void setBlackUsername(String blackUsername) {
    this.blackUsername = blackUsername;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }

  public ChessGame getGame() {
    return game;
  }

  public void setGame(ChessGame game) {
    this.game = game;
  }

  public Collection<String> getGameObserver() {
    return gameObserver;
  }

  public void addGameObserver(String username){
    gameObserver.add(username);
  }

  public void removeGameObserver(String username){
    gameObserver.remove(username);
  }

  public void setGameObserver(Collection<String> gameObserver) {
    this.gameObserver = gameObserver;
  }

  @Override
  public boolean equals(Object obj) {
    Game game1 = (Game) obj;
    boolean gameEquals = true;
    if (this == obj) return true;
    if(obj == null && this != null){
      return false;
    }
    if(obj != null && this == null){
      return false;
    }
    if(!(game1.getGameName().equals(this.getGameName()))){
      return false;
    }

    if((game1.getGameID() != this.getGameID())){
      return false;
    }

    if(game1.getBlackUsername() != this.getBlackUsername()){
      return false;
    }
    if(game1.getWhiteUsername() != this.getWhiteUsername()){
      return false;
    }
    if(!(game1.gameObserver.equals(this.gameObserver))){
      return false;
    }

    return gameEquals;
  }

//  @Override
//  public int hashCode() {
//    return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game, gameObserver);
//  }
}
