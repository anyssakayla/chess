package Model;

public class User {
  String username;
  String password;
  String email;

  /**
   * Represents a user
   *
   * @param username The string that represents a user's unique username
   * @param password The unique password that is associated with a user to log into the game
   * */
  public User(String username, String password, String email){
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public User(User givenUser){
    this.username = new String(givenUser.username);
    this.password = new String(givenUser.password);
    this.email = new String(givenUser.email);
  }
  public User(){}

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object obj) {
    User objUser = (User) obj;
    if(this == obj){
      return true;
    }
    if(this.getClass() != obj.getClass()){
      return false;
    }
    if(!this.getUsername().equals(objUser.getUsername())){
      return false;
    }
    if(!this.getPassword().equals(objUser.getPassword())){
      return false;
    }
    if(!this.getEmail().equals(objUser.getEmail())){
      return false;
    }
    return true;
  }

}
