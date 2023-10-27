package Service;
import chess.Model.Game;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import dataAccess.DAO.GameDao;
import Request.CreateGameReq;
import Result.CreateGameResult;
import dataAccess.DataAccessException;

public class CreateGameService {

  /**
   * Creates a game in the database
   *
   * @param request The create game request
   * @return A CreateGameResult object with information on if the request was successful
   * */
  public CreateGameResult createGame(CreateGameReq request) throws DataAccessException {
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();
    Game game = new Game();
    CreateGameResult result = new CreateGameResult();
    String gameName = request.getGameName();
    String requestAuthToken = request.getAuthToken();

    if(gameName == null || requestAuthToken == null){
      result.setMessage("Error: bad request");
      return result;
    }
    authDao.getAuth(requestAuthToken); //make sure the request authToken is in the database to create a game
    gameDao.insertGame(gameName);

    result.setGameID(gameDao.getGameID(gameName));
    return result;
  }
}
