package Request;

public class CreateGameReq {
  private String authToken;
  private String gameName;

  /**
   * The request data to create a game
   * @param gameName The unique name of the game to create
   */
  public CreateGameReq(String gameName){}

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
}