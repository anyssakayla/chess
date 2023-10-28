package Result;
import dataAccess.DAO.GameDao;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;

public class ClearAppResult {
  private String message;
  static GameDao gameDao = new GameDao();
  static UserDao userDao = new UserDao();
  static AuthDao authDao = new AuthDao();

  /**
   * The result data from a user's request to clear the application
   * @param message A potential error message
   */
  public ClearAppResult(String message){}
  public ClearAppResult(){}
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public static GameDao getGameDao() {
    return gameDao;
  }

  public static void setGameDao(GameDao gameDao) {
    ClearAppResult.gameDao = gameDao;
  }

  public static UserDao getUserDao() {
    return userDao;
  }

  public static void setUserDao(UserDao userDao) {
    ClearAppResult.userDao = userDao;
  }

  public static AuthDao getAuthDao() {
    return authDao;
  }

  public static void setAuthDao(AuthDao authDao) {
    ClearAppResult.authDao=authDao;
  }
}
