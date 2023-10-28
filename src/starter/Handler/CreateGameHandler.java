package Handler;
import Request.CreateGameReq;
import Result.CreateGameResult;
import Service.CreateGameService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Objects;

public class CreateGameHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson=new Gson();
    CreateGameService runService=new CreateGameService();

    CreateGameReq runRequest=gson.fromJson(request.body(), CreateGameReq.class);
    CreateGameResult runResult=runService.createGame(runRequest);

    //TODO: FIX THE RESULT MESSAGES
    if (Objects.equals(runResult.getMessage(), "Error: username/fix this")) {
      response.status(500); //set the status to 500 error status
    }
    if (Objects.equals(runResult.getMessage(), "Error: bad request")) {
      response.status(400);
    }
    if (Objects.equals(runResult.getMessage(), "Error: username already taken")) {
      response.status(403);
    }
    return gson.toJson(runResult);
  }
}
