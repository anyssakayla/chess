package Handler;
import Service.ListGameService;
import Request.ListGamesReq;
import Result.ListGameResult;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import java.io.IOException;
import java.util.Objects;

public class ListGamesHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    ListGameService runService=new ListGameService();

    ListGamesReq runRequest = gson.fromJson(request.body(), ListGamesReq.class);
    ListGameResult runResult = runService.listGames(runRequest);

    if(Objects.equals(runResult.getMessage(), "Error: description")){
      response.status(500); //set the status to 500 error status
    }
    if(Objects.equals(runResult.getMessage(), "Error: unauthorized")){
      response.status(401);
    }
//    if(Objects.equals(runResult.getMessage(), "Error: username already taken")){
//      response.status(403);
//    }
    return gson.toJson(runResult);
  }
}
