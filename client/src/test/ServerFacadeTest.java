
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ui.Client;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTest {
  private static ServerFacade server = new ServerFacade("http://localhost:8080");
  String userAuthToken;
  private static Client client = new Client();
  static String authToken;
  static String gameID;

  @BeforeAll
  static void setup(){
    server.clear();
    authToken = server.register("username1", "password1", "email1");
    gameID = server.createGame(authToken, "Game1");
  }
  @Test
  void loginTest(){
    var token = server.login("username1", "password1");
    assertNotNull(token);
  }

  @Test
  void loginFailTest(){
    var fakeToken = server.login("fakeUser", "fakePass");
    assertNull(fakeToken);
  }

  @Test
  void logout(){
    String[] loginArgs = {"username1", "password1"};
    var loginToken = client.handleLogin(loginArgs);
    String[] logout = {"username1", "password1"};
    String checkLogout = client.handleLogout(logout);

    assertEquals("", checkLogout);
  }

  @Test
  void logoutFail(){ //logging out before signing in should fail
    String[] logout = {"username1", "password1"};
    String checkLogout = client.handleLogout(logout);
    assertNotEquals(checkLogout, "Logged out");
  }

  @Test
  void register(){
    var token = server.register("newUser", "newPass", "newEmail");
    assertNotNull(token);
  }

  @Test
  void registerFail(){ //this user is already registered
    var token = server.register("username1", "password1", "email1");
    assertNull(token);
  }

  @Test
  void createGame(){
    var returnString = server.createGame(authToken,"successGame");
    assertNotNull(returnString);
  }

  @Test
  void createGameFail(){
    var returnString = server.createGame("fakeToken","failGame");
    assertNull(returnString);
  }

  @Test
  void list(){
    userAuthToken = server.login("username1", "password1");
    var returnString = server.list(userAuthToken);
    assertNotNull(returnString);
  }

  @Test
  void listFail(){
    var returnString = server.list("fakeToken");
    assertNull(returnString);
  }

  @Test
  void join(){
    var returnBool = server.joinGame(gameID, "WHITE", authToken);
    assertTrue(returnBool);
  }

  @Test
  void joinFail(){
    var returnBool = server.joinGame(gameID, "WHITE", "fake");
    assertFalse(returnBool);
  }
  @Test
  void observe(){
    var returnBool = server.joinGame(gameID, null, authToken);
    assertTrue(returnBool);
  }

  @Test
  void observeFail(){
    var returnBool = server.joinGame(gameID, null, "fake");
    assertFalse(returnBool);
  }
}
