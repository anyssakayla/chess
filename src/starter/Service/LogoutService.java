package Service;
import chess.Model.AuthToken;
import dataAccess.DAO.AuthDao;
import Request.LogoutReq;
import Result.LogoutResult;
import dataAccess.DataAccessException;

public class LogoutService {

  /**
   * Logs a user out of a game
   *
   * @param request The logout of game request
   * @return A LogoutResult object with information on if the request was successful
   * */
  public LogoutResult logout(LogoutReq request) throws DataAccessException {
    AuthDao authDao = new AuthDao(); //create the Authtoken to delete
    AuthToken authToken = new AuthToken(request.getAuthToken(), request.getUsername());
    authDao.removeAuth(authToken);
    return null;
  }
}
