package Handler;
import Request.LoginReq;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.Map;

//get the request
//create an object of the service
//perform service
//log if it worked to user
public class LoginHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try{
      if(exchange.getRequestMethod().toLowerCase().equals("post")){
        InputStream requestBody = exchange.getRequestBody();

      }

    }catch (IOException e){
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getRequestBody().close(); //close if doesn't work
      e.printStackTrace();
    }

  }

  private static Object readResponseBody(HttpURLConnection http) throws IOException {
    Object responseBody = "";
    try (InputStream respBody = http.getInputStream()) {
      InputStreamReader inputStreamReader = new InputStreamReader(respBody);
      responseBody = new Gson().fromJson(inputStreamReader, LoginReq.class);
    }
    return responseBody;
  }

  private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
    if (!body.isEmpty()) {
      http.setDoOutput(true);
      try (var outputStream = http.getOutputStream()) {
        outputStream.write(body.getBytes());
      }
    }
  }
}
