package Request;

public class JoinGameReq {
  private int gameID;
  private String authToken;
  private String teamColor;

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
}
