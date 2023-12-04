package ServiceTests;
import Request.LoginReq;
import Request.LogoutReq;
import Request.RegisterReq;
import Service.RegisterService;
import Service.LoginService;
import Service.LogoutService;
import Result.LoginResult;
import Result.LogoutResult;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.UserDao;
import dataAccess.DataAccessException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginLogoutTest {

  static GameDao gameDao = new GameDao();
  static UserDao userDao = new UserDao();
  static AuthDao authDao = new AuthDao();

  @BeforeAll
  public static void setUp() throws DataAccessException {
    gameDao.clearGames();
    userDao.clearUsersInDatabase();
    authDao.clearAuthTokensInDB();

    RegisterReq registerReq = new RegisterReq("Alilah", "password", "alilah@gmail.com");
    RegisterService regService = new RegisterService();
    regService.register(registerReq); //Adds the user to the database
  }

  @Test
  public void LoginTest()throws DataAccessException, IOException {

    LoginService loginService = new LoginService();
    LoginReq loginReq = new LoginReq("Alilah", "password");
    LoginResult loginResult = loginService.login(loginReq);

    assertNotNull(authDao.getAuthTokenByUsername("Alilah"));
  }

  @Test
  public void falseLogin()throws DataAccessException, IOException{
    AuthDao authDao = new AuthDao();
    LoginService loginService = new LoginService();
    LoginReq loginReq = new LoginReq("Fake User", "password");
    LoginResult loginResult = loginService.login(loginReq);

    assertEquals("Error: unauthorized", loginResult.getMessage());
  }

  @Test
  public void LogoutTest()throws DataAccessException, IOException{
    LoginService loginService = new LoginService(); //login Alilah
    LoginReq loginReq = new LoginReq("Alilah", "password");
    String authForlogout;
      LoginResult loginResult = loginService.login(loginReq);
      authForlogout = loginResult.getAuthToken().toString();

    //authDao.clearAuthTokensInDB();

    AuthDao authDao = new AuthDao();
    LogoutService logoutService = new LogoutService(); //Logout Alilah

    //    AuthToken joinToken = new AuthToken();
    //    joinToken.setUsername("TestUser");
    //    authDao.insertAuth(joinToken);
    //    createGameReq.setAuthToken(joinToken.getAuthToken());

    //String authString = authDao.getAuthTokenByUsername("Alilah").toString();
    LogoutReq logoutReq = new LogoutReq("Alilah", authForlogout);
    logoutService.logout(logoutReq.getAuthToken());

    assertNull(authDao.getAuth(authForlogout)); //make sure Alilah's authToken is gone
  }

  @Test
  public void LogoutFakeTest()throws DataAccessException{ //PASSES
    authDao.clearAuthTokensInDB();
    AuthDao authDao = new AuthDao();
    LogoutService logoutService = new LogoutService();
    LogoutReq logoutReq = new LogoutReq("fakeUsername", "fakeAuthToken");
    LogoutResult result = logoutService.logout(logoutReq.getAuthToken());

    assertEquals(result.getMessage(), "Error: unauthorized");
  }







}
