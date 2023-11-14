package ServiceTests;
import dataAccess.DAO.GameDao;
import chess.ChessGame;
import chess.Model.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class GameDAOTest {
  static GameDao gameDao = new GameDao();

  @BeforeAll
  public static void setup(){

    try {
      gameDao.clearGames();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void CreateGame()throws DataAccessException{
    Game gameToCreate = new Game();
    gameToCreate.setGameName("testGame");
    GameDao gameDao = new GameDao();
    gameDao.insertGame(gameToCreate);
    Game found = gameDao.findGameName("testGame");
    assertEquals(found.getGameName(), "testGame");
  }

  @Test
  public void findGame()throws DataAccessException{
    Game gameToCreate = new Game();
    gameToCreate.setGameName("testGame");

    int gameToCreateID = gameDao.insertGame(gameToCreate);
    gameToCreate.setGameID(gameToCreateID);
    assertEquals(gameDao.findGame(gameToCreate.getGameID()), gameToCreate);
  }

  @Test
  public void falseGame() throws DataAccessException{
    Game findFalseGame = gameDao.findGame(240);
    assertNull(findFalseGame);
  }

  @Test
  public void claimSpotBlack() throws DataAccessException{
    Game gameToCreate = new Game();
    gameToCreate.setGameName("claimBlackGame");
    int gameToCreateId = gameDao.insertGame(gameToCreate);
    gameDao.claimSpot("Test username", gameToCreateId, ChessGame.TeamColor.BLACK);
    Game claimBlack = gameDao.findGameName("claimBlackGame");
    assertNotNull(claimBlack.getBlackUsername());
  }

  @Test
  public void claimSpotWhite() throws DataAccessException{
    Game gameToCreate = new Game();
    gameToCreate.setGameName("claimWhiteGame");
    int gameToCreateId = gameDao.insertGame(gameToCreate);
    gameDao.claimSpot("White user", gameToCreateId, ChessGame.TeamColor.WHITE);
    Game claimWhite = gameDao.findGameName("claimWhiteGame");
    assertNotNull(claimWhite.getWhiteUsername());
  }


  @Test
  public void clearAllGames() throws DataAccessException{
    Game test1 = new Game(); test1.setGameName("Test1");
    Game test2 = new Game(); test2.setGameName("Test2");
    Game test3 = new Game(); test3.setGameName("Test3");
    Game test4 = new Game(); test4.setGameName("Test4");
    Game test5 = new Game(); test5.setGameName("Test5");
    Game test6 = new Game(); test6.setGameName("Test6");

    gameDao.insertGame(test1);
    gameDao.insertGame(test2);
    gameDao.insertGame(test3);
    gameDao.insertGame(test4);
    gameDao.insertGame(test5);
    gameDao.insertGame(test6);
    gameDao.clearGames();
    assertTrue(gameDao.findAll().isEmpty());
  }

  @Test
  public void returnAllGames() throws DataAccessException{
    Collection<Game> allGames = new HashSet<Game>();
    gameDao.clearGames();
    Game test1 = new Game(); test1.setGameName("Test1"); //create games to add
    Game test2 = new Game(); test2.setGameName("Test2");
    Game test3 = new Game(); test3.setGameName("Test3");
    Game test4 = new Game(); test4.setGameName("Test4");
    Game test5 = new Game(); test5.setGameName("Test5");
    Game test6 = new Game(); test6.setGameName("Test6");

    int test1ID = gameDao.insertGame(test1); //insert these games into the
    int test2ID = gameDao.insertGame(test2);
    int test3ID = gameDao.insertGame(test3);
    int test4ID = gameDao.insertGame(test4);
    int test5ID = gameDao.insertGame(test5);
    int test6ID = gameDao.insertGame(test6);

    test1.setGameID(test1ID); allGames.add(test1); //make sure to set the ID before adding
    test2.setGameID(test2ID); allGames.add(test2);
    test3.setGameID(test3ID); allGames.add(test3);
    test4.setGameID(test4ID); allGames.add(test4);
    test5.setGameID(test5ID); allGames.add(test5);
    test6.setGameID(test6ID); allGames.add(test6);

    Collection<Game> gamesInDao = gameDao.findAll();
    Collection<String> gameStrings = new HashSet<>();
    for(Game games : gamesInDao){
      String name = games.getGameName();
      gameStrings.add(name);
    }
    for(String name : gameStrings){
      assertTrue(gameStrings.contains("Test1"));
      assertTrue(gameStrings.contains("Test2"));
      assertTrue(gameStrings.contains("Test3"));
      assertTrue(gameStrings.contains("Test4"));
      assertTrue(gameStrings.contains("Test5"));
      assertTrue(gameStrings.contains("Test6"));
    }
    assertTrue(gamesInDao.size() == allGames.size());
    //assertEquals(allGames, gameDao.findAll());
  }

  @AfterAll
  public static void takeDown() throws DataAccessException {
    gameDao.clearGames();
  }
}
