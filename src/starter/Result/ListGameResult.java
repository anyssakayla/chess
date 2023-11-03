package Result;
import chess.Model.Game;

import java.util.Collection;

public class ListGameResult {
  private Collection<Game> games; //should i use an arrayList?
  //used to be ArrayList<HashMap<String, Object>> gamesInDatabase
  private String message;

  /**
   * The result data for all the games listed under a user
   * @param message in case an error occurs
   * @param games the list of games to return
   */
  public ListGameResult(String message, Collection<Game> games){}
  public ListGameResult(){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public Collection<Game> getGames() {
    return games;
  }

  public void setGames(Collection<Game> games) {
    this.games=games;
  }
}
