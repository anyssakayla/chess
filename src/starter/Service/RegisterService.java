package Service;
import Model.AuthToken;
import Model.User;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import Request.RegisterReq;
import Result.RegisterResult;
import dataAccess.DataAccessException;

public class RegisterService {

  /**
   * Registers a new user in the database
   *
   * @param request The register request
   * @return A RegisterResult object with information on if the request was successful
   * */
  public RegisterResult register(RegisterReq request) throws DataAccessException {
    User user = new User();
    AuthToken authToken = new AuthToken(); //also sets random authToken
    AuthDao authDao = new AuthDao();
    UserDao userDao = new UserDao();
    RegisterResult result = new RegisterResult();

    //check if anything is null to begin with
    if(request.getPassword() == null || request.getEmail() == null || request.getUsername() == null){
      result.setMessage("Error: bad request");
      return result;
    }
    if(userDao.checkIfTaken(request.getUsername())== null){ //if username is already taken, send message
      result.setMessage("Error: already taken");
      return result;
    }

    authToken.setUsername(request.getUsername()); //set authToken for the result
    authDao.insertAuth(authToken); //insert authToken to Dao


    user.setUsername(request.getUsername()); //set user parameters
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    userDao.insertUser(user);

    //set the result information
    result.setAuthToken(authToken.getAuthToken()); //TODO: Check if we have to use Dao since token is created and stored there
    result.setUsername(request.getUsername());
    result.setEmail(request.getEmail());
    result.setPassword(request.getPassword()); //do we need this one?

    return result;
  }
}
