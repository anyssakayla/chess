package Result;

public class JoinGameResult {
  private String message;
  private int gameID;

  /**
   * The result data for a user's request to join a game
   * @param gameID The unique ID of the game the user wants to join
   * @param message in case an error occurs
   */
  public JoinGameResult(String message, int gameID){}
  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID=gameID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
