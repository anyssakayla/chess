package Service;
import Result.RegisterResult;
import chess.Model.AuthToken;
import chess.Model.User;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import Request.LoginReq;
import Result.LoginResult;
import dataAccess.DataAccessException;

import java.io.IOException;

public class LoginService {

  /**
   * Logs a user into the database and gives them an authToken
   *
   * @param request The login request
   * @return A LoginResult object with information on if the request was successful
   * */
  public LoginResult login(LoginReq request) throws DataAccessException, IOException {
    User user = new User();
    AuthToken authToken = new AuthToken();
    AuthDao authDao = new AuthDao();
    UserDao userDao = new UserDao();
    LoginResult result = new LoginResult();

    if(userDao.findUser(request.getUsername())== null){ //if username is already taken, send message
      result.setMessage("Error: unauthorized");
      return result;
    }
    user = userDao.findUser(request.getUsername()); //get the user based off the request username
    if(!request.getPassword().equals(user.getPassword())){ //if the password is incorrect
      result.setMessage("Password incorrect");
      throw new IOException("Error: unauthorized");
    }
    result.setUsername(request.getUsername());
    result.setAuthToken(authDao.getAuthStringByUsername(request.getUsername()));
    return result;}
}
