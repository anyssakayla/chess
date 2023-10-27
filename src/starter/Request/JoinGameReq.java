package Request;

import Result.JoinGameResult;

public class JoinGameReq {
  private int gameID;
  private String authToken;
  private String teamColor;
  private String username;
  /**
   * The request data for a user to join a game
   * @param gameID The unique ID of the game the user wants to join
   * @param teamColor the color of the team the user wants to join as
   */
  public JoinGameReq(String teamColor, Integer gameID){
    this.gameID = gameID;
    this.teamColor = teamColor;
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

  public String getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(String teamColor) {
    this.teamColor=teamColor;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }
}
