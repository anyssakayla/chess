package passoffTests.serverTests;
import Request.LoginReq;
import Request.LogoutReq;
import Request.RegisterReq;
import Result.RegisterResult;
import Service.RegisterService;
import chess.Model.User;
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

import javax.xml.crypto.Data;
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

    //userDao.findUser("Alilah"); //userDatabase is empty
    LoginService loginService = new LoginService();
    LoginReq loginReq = new LoginReq("Alilah", "password");
    LoginResult loginResult = loginService.login(loginReq);

    assertNotNull(authDao.getAuthStringByUsername("Alilah"));
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
  public void LogoutTest()throws DataAccessException{ //PASSES
    authDao.clearAuthTokensInDB();
    AuthDao authDao = new AuthDao();
    LogoutService logoutService = new LogoutService();
    LogoutReq logoutReq = new LogoutReq("Alilah", authDao.getAuthStringByUsername("Alilah"));
    logoutService.logout(logoutReq.getAuthToken());

    assertTrue(authDao.findAll().size() == 0);
  }







}
