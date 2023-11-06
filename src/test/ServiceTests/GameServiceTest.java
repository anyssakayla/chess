package ServiceTests;
import Request.*;
import Result.*;
import chess.Model.AuthToken;
import chess.Model.User;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import dataAccess.DAO.GameDao;
import Service.*;
import chess.ChessGame;
import chess.Model.Game;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GameServiceTest { //ALL PASS
  static GameDao gameDao = new GameDao();
  static UserDao userDao = new UserDao();
  static AuthDao authDao = new AuthDao();

  @BeforeAll
  public static void setUp() throws DataAccessException{


    gameDao.clearGames();
    userDao.clearUsersInDatabase();
    authDao.clearAuthTokensInDB();

    RegisterReq registerReq = new RegisterReq("Alilah", "password", "alilah@gmail.com");
    RegisterService regService = new RegisterService();
    regService.register(registerReq);

    RegisterReq registerReq2 = new RegisterReq("Michelle", "Password", "Michelle@gmail.com");
    RegisterService regService2 = new RegisterService();
    regService.register(registerReq2);

    RegisterReq registerReq3 = new RegisterReq("TestUser", "pass", "test@gmail.com");
    RegisterService regService3 = new RegisterService();
    regService.register(registerReq3);
  }

  @Test
  public void CreateGamesTest() throws DataAccessException {
    User user;
    AuthToken authToken;
    AuthToken currentAuthToken;
    UserDao userDao = new UserDao();
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();

    CreateGameReq createRequest = new CreateGameReq("Game 1");
    CreateGameService createService = new CreateGameService();
    createRequest.setAuthToken(authDao.getAuthTokenByUsername("Alilah").getAuthToken());

    createService.createGame(createRequest, "working");
     assertNotNull(gameDao.findAll());
     
  }

  @Test
  public void CreateFalseGame()throws DataAccessException{
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();

    CreateGameReq createRequest = new CreateGameReq("Game 1");
    CreateGameService createService = new CreateGameService();

    authDao.clearAuthTokensInDB();
    //set request's authToken before passing it into create game
    createRequest.setAuthToken(authDao.getAuthStringByUsername("Alilah")); //need authString or token?
    CreateGameResult runResult = createService.createGame(createRequest, "testAuthtoken");
    assertEquals("Error: unauthorized", runResult.getMessage());
  }

  @Test
  public void ListGamesTest()throws DataAccessException{
    gameDao.clearGames();
    CreateGameService gameService = new CreateGameService();
    ListGameService listGameService = new ListGameService();
    //trying to get all games listed under a name?
//    ListGameService listGameService = new ListGameService();
//    ListGamesReq listGamesReq = new ListGamesReq(authDao.getAuthStringByUsername("Alilah"));
//    listGamesReq.setAuthToken(authDao.getAuthTokenByUsername("Alilah").getAuthToken());
//    ListGameResult listGameResult = listGameService.listGames(listGamesReq); //use authToken?
//
//    assertNull(listGameResult.getGamesInDatabase());

    CreateGameReq gameReq1 = new CreateGameReq("Game 1");
    gameReq1.setAuthToken(authDao.insertAuth("Alilah").getAuthToken()); //TODO: change to toString()?
    CreateGameReq gameReq2 = new CreateGameReq("Game 2");
    gameReq2.setAuthToken(authDao.insertAuth("Michelle").getAuthToken());
    CreateGameReq gameReq3 = new CreateGameReq("Game 3");
    gameReq3.setAuthToken(authDao.insertAuth("TestUser").getAuthToken());

    gameService.createGame(gameReq1, authDao.insertAuth("Alilah").getAuthToken());
    gameService.createGame(gameReq2, authDao.insertAuth("Michelle").getAuthToken());
    gameService.createGame(gameReq3, authDao.insertAuth("TestUser").getAuthToken());

    assertEquals(gameDao.findAll().size(), 3);

  }

  @Test
  public void falseListGame()throws DataAccessException{
    gameDao.clearGames();
    ListGameService listGameService = new ListGameService();
    ListGamesReq listGamesReq = new ListGamesReq("fake game");
    String auth = listGamesReq.getAuthToken();
    ListGameResult listGameResult = listGameService.listGames(auth);

    assertTrue(gameDao.findAll().isEmpty());
  }


  @Test
  public void JoinGameTest()throws DataAccessException, IOException{
    gameDao.clearGames();
    CreateGameService createGameService = new CreateGameService();
    JoinGameService joinGameService = new JoinGameService();
    GameDao gameDao = new GameDao();
    AuthDao authDao = new AuthDao();

    CreateGameReq createGameReq = new CreateGameReq("joingame1");
    authDao.insertAuth("Alilah");
    //createGameReq.setAuthToken(authDao.getAuth("Alilah").getAuthToken());
    CreateGameResult createGameResult = createGameService.createGame(createGameReq, authDao.getAuthStringByUsername("Alilah"));

    int IDForGame =createGameResult.getGameID();
    JoinGameReq joinGameReq = new JoinGameReq("white", IDForGame);
    joinGameReq.setUsername("Alilah");
    joinGameReq.setAuthToken(authDao.getAuthStringByUsername("Alilah"));
    joinGameService.joinGame(joinGameReq, authDao.getAuthStringByUsername("Alilah"));


    assertEquals("Alilah", gameDao.findGame(IDForGame).getWhiteUsername());
  }

  @Test
  public void FailJoinGameTest()throws DataAccessException, IOException{
    CreateGameService createGameService = new CreateGameService();
    JoinGameService joinGameService = new JoinGameService();
    GameDao gameDao = new GameDao();
    AuthDao authDao = new AuthDao();

    CreateGameReq createGameReq = new CreateGameReq("joingame1");
    authDao.insertAuth("Alilah");
    //createGameReq.setAuthToken(authDao.getAuth("Alilah").getAuthToken());
    CreateGameResult createGameResult = createGameService.createGame(createGameReq, authDao.getAuthStringByUsername("Alilah"));

    int IDForGame = createGameResult.getGameID();
    JoinGameReq joinGameReq = new JoinGameReq("white", IDForGame);
    joinGameReq.setUsername("Alilah");
    joinGameReq.setAuthToken(authDao.getAuthStringByUsername("Alilah"));
    joinGameService.joinGame(joinGameReq, authDao.getAuthStringByUsername("Alilah"));

    JoinGameReq joinGameReq2 = new JoinGameReq("white", IDForGame);
    joinGameReq.setUsername("Michelle");
    joinGameReq.setAuthToken(authDao.getAuthStringByUsername("Michelle"));
    JoinGameResult result = joinGameService.joinGame(joinGameReq2, authDao.getAuthStringByUsername("Michelle"));
    assertEquals("Error: already taken", result.getMessage());
  }
  //Create another join

  @Test
  public void clearApplicationTest()throws DataAccessException{
    ClearService clearService = new ClearService();
    CreateGameService createGameService = new CreateGameService();
    UserDao userDao = new UserDao();
    GameDao gameDao = new GameDao();
    AuthDao authDao= new AuthDao();

    CreateGameReq createGameReq= new CreateGameReq("joingame1");
    authDao.insertAuth("Alilah");
    createGameReq.setAuthToken(authDao.insertAuth("Alilah").getAuthToken());
    CreateGameResult createGameResult = createGameService.createGame(createGameReq, "testAuths");

    assertNotNull(gameDao.findAll());

    ClearAppReq clearAppReq = new ClearAppReq();
    clearService.clearApplication(clearAppReq);

    assertEquals(userDao.findAllUsers().size(), 0);
    assertEquals(gameDao.findAll().size(), 0);
    assertEquals(authDao.findAll().size(), 0);


  }
}
