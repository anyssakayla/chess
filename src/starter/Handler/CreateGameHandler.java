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
    String authToken = request.headers("authorization");
    CreateGameResult runResult=runService.createGame(runRequest, authToken);

    response.status(200);
    if (Objects.equals(runResult.getMessage(), "Error: description")) {
      response.status(500); //set the status to 500 error status
    }
    if (Objects.equals(runResult.getMessage(), "Error: bad request")) {
      response.status(400);
    }
    if (Objects.equals(runResult.getMessage(), "Error: unauthorized")) {
      response.status(401);
    }
    return gson.toJson(runResult);
  }
}
