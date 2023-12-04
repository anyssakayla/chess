package Service;
import dataAccess.DAO.AuthDao;
import Result.LogoutResult;
import dataAccess.DataAccessException;

public class LogoutService {

  /**
   * Logs a user out of a game
   *
   * @param auth The authToken of the person to logout
   * @return A LogoutResult object with information on if the request was successful
   * */
  public LogoutResult logout(String auth) throws DataAccessException {
    //see if auth exists
    LogoutResult logoutResult = new LogoutResult();
    AuthDao authDao = new AuthDao(); //create the Authtoken to delete

    if(authDao.getAuth(auth) == null){
      logoutResult.setMessage("Error: unauthorized");
      return logoutResult;
    }
    authDao.removeAuth(auth);

    logoutResult.setAuthToken(auth);
    return logoutResult;
  }
}
