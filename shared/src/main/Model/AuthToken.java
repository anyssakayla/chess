package Model;
import java.util.UUID;

public class AuthToken {
  String authToken;
  String username;

  /**
   * Clears all information from the database
   * @param authToken a unique token for a user
   * @param username The string that represents the user's authorization token
   *
   * */
 public AuthToken(String authToken, String username){
    this.authToken = authToken;
    this.username = username;
  }

  public AuthToken(){
   authToken = UUID.randomUUID().toString();
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object obj) {
    AuthToken objAuth = (AuthToken) obj;

    if(this == obj){ //if equal, return true right away
      return true;
    }
    if(!(obj.getClass() == this.getClass())){
      return false;
    }

    if(!objAuth.username.equals(this.username)){
      return false;
    }
    if(!objAuth.authToken.equals(this.authToken)){
      return false;
    }
    return true; //met all requirements
  }
}
