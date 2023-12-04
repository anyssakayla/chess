package Handler;
import Service.LogoutService;
import Result.LogoutResult;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler {

  public String handle(Request request, Response response) throws DataAccessException {
    Gson gson = new Gson();
    LogoutService runService = new LogoutService();

    //LogoutReq runRequest = gson.fromJson(request.body(), LogoutReq.class);
    String token = request.headers("authorization");
    LogoutResult runResult = runService.logout(token);


    if(Objects.equals(runResult.getMessage(), "Error: description")){
      response.status(500); //set the status to 500 error status
    }
    if(Objects.equals(runResult.getMessage(), "Error: unauthorized")){
      response.status(401);
    }
//    if(Objects.equals(runResult.getMessage(), "Error: username already taken")){
//      response.status(403);
//    }
    return gson.toJson(runResult); //if no errors occur, return the result given by
  }
}
