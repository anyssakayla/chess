package passoffTests.serverTests;

import Request.RegisterReq;
import Result.RegisterResult;
import Service.RegisterService;
import chess.Model.User;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.UserDao;
import dataAccess.DataAccessException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegisterServiceTest {
  static GameDao gameDao = new GameDao();
 static UserDao userDao = new UserDao();
  static AuthDao authDao = new AuthDao();

  @BeforeAll
  public static void setUp() throws DataAccessException {


    gameDao.clearGames();
    userDao.clearUsersInDatabase();
    authDao.clearAuthTokensInDB();

    RegisterReq registerReq = new RegisterReq("Alilah", "password", "alilah@gmail.com");
    //REquest needs an authToken
    RegisterService regService = new RegisterService(); //TODO: register needs to give user an authtoken
    regService.register(registerReq);
  }

  @Test
  public void RegisterTest()throws DataAccessException{
   // UserDao userDao = new UserDao();
   // AuthDao authDao = new AuthDao();

    User userToTest = userDao.findUser("Alilah");
    assertEquals(userToTest.getUsername(), "Alilah");
    assertEquals(userToTest.getPassword(),"password");
    assertEquals(userToTest.getEmail(), "alilah@gmail.com");

    assertFalse(authDao.findAll().isEmpty());
  }

  @Test
  public void failRegisterTest() throws DataAccessException{
    RegisterReq registerReq = new RegisterReq("Alilah", "password", "alilah@gmail.com");
    RegisterService service = new RegisterService();
    RegisterResult result = service.register(registerReq);
    assertEquals("Error: already taken", result.getMessage());
  }
  @Test
  public void secondFailRegister() throws DataAccessException{
    RegisterReq registerReq = new RegisterReq();
    RegisterService runService = new RegisterService();
    registerReq.setUsername("Alilah");
    registerReq.setPassword("password");

    RegisterResult runResult = runService.register(registerReq);
    assertEquals("Error: bad request", runResult.getMessage());
  }
}
