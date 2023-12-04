package Result;

public class JoinGameResult {
  private String message;
  private int gameID;
  private String authToken;
  private String teamColor;
  private String username;

  /**
   * The result data for a user's request to join a game
   * @param gameID The unique ID of the game the user wants to join
   * @param message in case an error occurs
   */
  public JoinGameResult(String message, int gameID){
    this.message = message;
    this.gameID = gameID;
  }
  public JoinGameResult(){}
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

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(String teamColor) {
    this.teamColor = teamColor;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
