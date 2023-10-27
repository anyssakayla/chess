package Result;

public class CreateGameResult {
  private int gameID;
  private String message;
  /**
   * The result data for a user's request to create a game
   * @param message in case an error occurs
   * @param gameID the ID for the game to be made
   */
public CreateGameResult(String message, int gameID){}
  public CreateGameResult(){}
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
