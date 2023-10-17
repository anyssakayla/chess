package Request;

public class CreateGameReq {
  private String authToken;
  private String gameName;

  public CreateGameReq(){}

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
