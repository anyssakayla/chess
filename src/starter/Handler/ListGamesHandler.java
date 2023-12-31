package Handler;
import Service.ListGameService;
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

    String auth = request.headers("Authorization");
    ListGameResult runResult = runService.listGames(auth);

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
