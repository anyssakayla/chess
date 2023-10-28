package Handler;
import Service.LogoutService;
import Request.LogoutReq;
import Result.LogoutResult;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Objects;

public class LogoutHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    LogoutService runService=new LogoutService();

    LogoutReq runRequest = gson.fromJson(request.body(), LogoutReq.class);
    LogoutResult runResult = runService.logout(runRequest);

    //TODO: FIX THE RESULT MESSAGES
    if(Objects.equals(runResult.getMessage(), "Error: username/fix this")){
      response.status(500); //set the status to 500 error status
    }
    if(Objects.equals(runResult.getMessage(), "Error: bad request")){
      response.status(400);
    }
    if(Objects.equals(runResult.getMessage(), "Error: username already taken")){
      response.status(403);
    }
    return gson.toJson(runResult); //if no errors occur, return the result given by
  }
}
