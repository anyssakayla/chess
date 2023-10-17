package Request;

public class ListGamesReq {
  private String authToken;

  public ListGamesReq(){}

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
