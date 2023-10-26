package Result;
import Request.ListGamesReq;
import chess.Model.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListGameResult {
  private ArrayList<HashMap<String, Object>> gamesInDatabase; //should i use an arrayList?
  private String message;

  /**
   * The result data for all the games listed under a user
   * @param message in case an error occurs
   * @param gamesInDatabase the list of games to return
   */
  public ListGameResult(String message, ArrayList<HashMap<String, Object>> gamesInDatabase){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public ArrayList<HashMap<String, Object>> getGamesInDatabase() {
    return gamesInDatabase;
  }

  public void setGamesInDatabase(ArrayList<HashMap<String, Object>> gamesInDatabase) {
    this.gamesInDatabase=gamesInDatabase;
  }
}
