package Result;
import Request.ListGamesReq;
import chess.Model.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListGameResult {
  private Collection<Game> gamesInDatabase; //should i use an arrayList?
  //used to be ArrayList<HashMap<String, Object>> gamesInDatabase
  private String message;

  /**
   * The result data for all the games listed under a user
   * @param message in case an error occurs
   * @param gamesInDatabase the list of games to return
   */
  public ListGameResult(String message, Collection<Game> gamesInDatabase){}
  public ListGameResult(){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public Collection<Game> getGamesInDatabase() {
    return gamesInDatabase;
  }

  public void setGamesInDatabase(Collection<Game> gamesInDatabase) {
    this.gamesInDatabase=gamesInDatabase;
  }
}
