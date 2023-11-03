package Service;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.AuthDao;
import Result.ListGameResult;
import dataAccess.DataAccessException;

public class ListGameService {

  /**
   * Lists all the games in the database
   *
   * @param auth The authToken to list all the games
   * @return A ListGameResult object with information on if the request was successful
   * */
  public ListGameResult listGames(String auth) throws DataAccessException{
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();
    ListGameResult result = new ListGameResult();

    if(authDao.getAuth(auth) == null){
      result.setMessage("Error: unauthorized");
      return result;
    }
    result.setGames(gameDao.findAll());
    return result;
  }
}
