package Handler;
import Request.RegisterReq;
import Result.RegisterResult;
import Service.RegisterService;
import com.google.gson.Gson;
import java.io.*;
import java.net.HttpURLConnection;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Objects;

public class RegisterHandler {

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    RegisterService runService = new RegisterService();

    RegisterReq runRequest = gson.fromJson((request.body()), RegisterReq.class);
    RegisterResult runResult = runService.register(runRequest);


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
    return gson.toJson(runResult); //if no errors occur, return the result given by the service as a Json
  }
}
