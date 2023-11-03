package Handler;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import java.io.IOException;
import java.util.Objects;
import Request.JoinGameReq;
import Result.JoinGameResult;
import Service.JoinGameService;

public class JoinGameHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    JoinGameService runService = new JoinGameService();

    String auth = request.headers("authorization");
    JoinGameReq runRequest = gson.fromJson(request.body(), JoinGameReq.class);
    JoinGameResult runResult = runService.joinGame(runRequest, auth);


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
    if (Objects.equals(runResult.getMessage(), "Error: already taken")) {
      response.status(403);
    }
    return gson.toJson(runResult);
  }
}
