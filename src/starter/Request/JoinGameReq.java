package Request;

public class JoinGameReq {
  private int gameID;
  private String authToken;
  private String playerColor;
  private String username;
  /**
   * The request data for a user to join a game
   * @param gameID The unique ID of the game the user wants to join
   * @param playerColor the color of the team the user wants to join as
   */
  public JoinGameReq(String playerColor, Integer gameID){
    this.gameID = gameID;
    this.playerColor=playerColor;
  }

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID=gameID;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(String playerColor) {
    this.playerColor=playerColor;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }
}
