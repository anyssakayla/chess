package Request;

public class CreateGameReq {
  private String authToken;
  private String gameName;
  private int gameID;

  /**
   * The request data to create a game
   * @param gameName The unique name of the game to create
   */
  public CreateGameReq(String gameName){
    this.gameName = gameName;
  }
  public CreateGameReq(){}

  public CreateGameReq(String gameName, String authToken){
    this.gameName = gameName;
    this.authToken = authToken;
  }

  public void setGameName(String gameName){
    this.gameName = gameName;
  }

  public String getGameName(){
    return gameName;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID=gameID;
  }
}
