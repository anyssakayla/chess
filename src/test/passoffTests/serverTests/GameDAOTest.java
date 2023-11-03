package passoffTests.serverTests;
import dataAccess.DAO.GameDao;
import chess.ChessGame;
import chess.Model.Game;
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
    gameDao.clearGames();
  }

  @Test
  public void CreateGame()throws DataAccessException{
    Game gameToCreate = gameDao.insertGame("testGame");
    assertEquals(gameToCreate.getGameName(), "testGame");
  }

  @Test
  public void findGame()throws DataAccessException{
    Game gameToCreate = gameDao.insertGame("testGame");
    assertEquals(gameDao.findGame(gameToCreate.getGameID()), gameToCreate);
  }

  @Test
  public void falseGame() throws DataAccessException{
    Game findFalseGame = gameDao.findGame(240);
    assertNull(findFalseGame);
  }

  @Test
  public void claimSpotBlack() throws DataAccessException{
    Game gameToCreate = gameDao.insertGame("testGame");
    gameDao.claimSpot("Test username", gameToCreate.getGameID(), ChessGame.TeamColor.BLACK);
    Game testGame = new Game(gameToCreate.getGameID(), "testGame");
    testGame.setBlackUsername("Test username");
    assertEquals(gameToCreate, testGame);
  }

  @Test
  public void claimSpotWhite() throws DataAccessException{
    Game gameToCreate = gameDao.insertGame("testGame");
    gameDao.claimSpot("Test username", gameToCreate.getGameID(), ChessGame.TeamColor.WHITE);
    Game testGame = new Game(gameToCreate.getGameID(), "testGame");
    testGame.setWhiteUsername("Test username");
    assertEquals(gameToCreate, testGame);
  }

  @Test
  public void ClaimObserverSpot() throws DataAccessException{
    Game gameToCreate = gameDao.insertGame("testGame");
    gameDao.claimSpot("Test username", gameToCreate.getGameID(), null);
    Game testGame = new Game(gameToCreate.getGameID(), "testGame");
    testGame.addGameObserver("Test username");
    assertEquals(testGame, gameToCreate);
  }

  @Test
  public void ClaimFalseSpot()throws DataAccessException{

    assertThrows(DataAccessException.class, () -> gameDao.claimSpot("False user", 242, ChessGame.TeamColor.WHITE));
  }

  @Test
  public void clearAllGames() throws DataAccessException{
    Collection<Game> allGames = new HashSet<Game>();
    allGames.add(gameDao.insertGame("Test1"));
    allGames.add(gameDao.insertGame("Test2"));
    allGames.add(gameDao.insertGame("Test3"));
    allGames.add(gameDao.insertGame("Test4"));
    allGames.add(gameDao.insertGame("Test5"));
    allGames.add(gameDao.insertGame("Test6"));
    gameDao.clearGames();
    assertTrue(gameDao.findAll().isEmpty());
  }

  @Test
  public void returnAllGames() throws DataAccessException{
    Collection<Game> allGames = new HashSet<Game>();
    gameDao.clearGames();
    allGames.add(gameDao.insertGame("Test1"));
    allGames.add(gameDao.insertGame("Test2"));
    allGames.add(gameDao.insertGame("Test3"));
    allGames.add(gameDao.insertGame("Test4"));
    allGames.add(gameDao.insertGame("Test5"));
    allGames.add(gameDao.insertGame("Test6"));
    Collection<Game> gamesInDao = gameDao.findAll();
    for(Game games : gamesInDao){
      assertTrue(allGames.contains(games));
    }
    assertTrue(gamesInDao.size() == allGames.size());
    //assertEquals(allGames, gameDao.findAll());
  } //TODO: Fix after all

  @AfterAll
  public static void takeDown(){
    gameDao.clearGames();
  }
}
