package Handler;
import Service.ClearService;
import Request.ClearAppReq;
import Result.ClearAppResult;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import java.io.IOException;
import java.util.Objects;

public class ClearHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    ClearService runService = new ClearService();

    ClearAppReq runRequest = gson.fromJson(request.body(), ClearAppReq.class);
    ClearAppResult runResult = runService.clearApplication(runRequest);


    if(Objects.equals(runResult.getMessage(), "Error: description")){
      response.status(500); //set the status to 500 error status
    }
//    if(Objects.equals(runResult.getMessage(), "Error: bad request")){
//      response.status(400);
//    }
//    if(Objects.equals(runResult.getMessage(), "Error: username already taken")){
//      response.status(403);
//    }
    return gson.toJson(runResult);
  }
}
