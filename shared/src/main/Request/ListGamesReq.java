package Request;

public class ListGamesReq {
  private String authToken;

  /**
   * The request data for all the games listed under a user
   * @param authToken the user's token to find the games
   */
  public ListGamesReq(String authToken){
    this.authToken = authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
