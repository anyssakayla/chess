package chess.Model;
import java.util.Collection;
import java.util.HashSet;
import chess.ChessGame;
import chess.ChessGameImpl;

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
}
