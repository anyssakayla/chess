package Service;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import Request.ClearAppReq;
import Result.ClearAppResult;
import dataAccess.DataAccessException;

public class ClearService {
  public ClearService(){}
  static GameDao gameDao = new GameDao();
  static UserDao userDao = new UserDao();
  static AuthDao authDao = new AuthDao();
  /**
   * Clears all information from the database
   * @return A ClearResult object with information on if the request was completed
   * */
  public ClearAppResult clearApplication(ClearAppReq request) throws DataAccessException {
    ClearAppResult result = new ClearAppResult();
    gameDao.clearGames();
    userDao.clearUsersInDatabase();
    authDao.clearAuthTokensInDB();

    return result;
  }
}
