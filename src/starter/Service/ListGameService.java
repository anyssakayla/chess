package Service;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.AuthDao;
import Request.ListGamesReq;
import Result.ListGameResult;
import dataAccess.DataAccessException;
import java.io.IOException;
import java.util.Locale;

public class ListGameService {

  /**
   * Lists all the games in the database
   *
   * @param request The listGames request
   * @return A ListGameResult object with information on if the request was successful
   * */
  public ListGameResult listGames(ListGamesReq request) throws DataAccessException{
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();
    ListGameResult result = new ListGameResult();
    String requestAuth = request.getAuthToken();

    if(request.getAuthToken().isEmpty()){
      result.setMessage("Error: bad request");
    }
    authDao.getAuth(requestAuth); //make sure the request authToken is in the database
    result.setGamesInDatabase(gameDao.findAll());
    return result;
  }
}
