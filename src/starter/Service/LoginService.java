package Service;
import Model.AuthToken;
import Model.User;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import Request.LoginReq;
import Result.LoginResult;
import dataAccess.DataAccessException;

import java.io.IOException;
import java.util.UUID;

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
      result.setMessage("Error: unauthorized");
      return result;
      //throw new IOException("Error: unauthorized");
    }
    result.setUsername(request.getUsername());
    AuthToken token = new AuthToken(UUID.randomUUID().toString(), request.getUsername());
    result.setAuthToken(token.getAuthToken());
    //    authToken.setUsername(request.getUsername());
    //    authDao.insertAuth(authToken);
    token.setUsername(request.getUsername());
    authDao.insertAuth(token); //this is not adding to the database;


    return result;}
}
