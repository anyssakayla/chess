package Handler;
import Request.LoginReq;
import Result.LoginResult;
import Service.LoginService;
import Service.RegisterService;
import com.google.gson.Gson;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Objects;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

//get the request
//create an object of the service
//perform service
//log if it worked to user
public class LoginHandler{

  public String handle(Request request, Response response) throws IOException, DataAccessException {
    Gson gson = new Gson();
    LoginService runService = new LoginService();

    LoginReq runRequest = gson.fromJson((request.body()), LoginReq.class); //converts HTTP request
    LoginResult runResult = runService.login(runRequest); //run the service with the request to get response

    if(Objects.equals(runResult.getMessage(), "Error: description")){
      response.status(500); //set the status to 500 error status
    }
//    if(Objects.equals(runResult.getMessage(), "Error: bad request")){
//      response.status(400);
//    }
    if(Objects.equals(runResult.getMessage(), "Error: unauthorized")){
      response.status(401);
    }
    return gson.toJson(runResult); //if no errors occur, return the result given by the service as a Json
  }


  private static Object readResponseBody(HttpURLConnection http) throws IOException {
    Object responseBody = "";
    try (InputStream respBody = http.getInputStream()) {
      InputStreamReader inputStreamReader = new InputStreamReader(respBody);
      responseBody = new Gson().fromJson(inputStreamReader, LoginReq.class);
    }
    return responseBody;
  }

  private String readString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }

  private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
    if (!body.isEmpty()) {
      http.setDoOutput(true);
      try (var outputStream = http.getOutputStream()) {
        outputStream.write(body.getBytes());
      }
    }
  }


  /**
   * The writeString method shows how to write a String to an OutputStream.
   * @param str string to write
   * @param os output stream to write
   * @throws IOException in case of error while reading files
   */
  private void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    sw.write(str);
    sw.flush();
  }
}
