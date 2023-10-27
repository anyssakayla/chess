package Handler;
import Request.LoginReq;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.Map;

import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

//get the request
//create an object of the service
//perform service
//log if it worked to user
public class LoginHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try{
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        // Extract the JSON string from the HTTP request body
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        System.out.println(reqData);

        // Create request to pass into service
        Gson gson = new Gson();
        LoginReq request = gson.fromJson(reqData, LoginReq.class);

        // Claim a route based on the request data
        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        // Send response to client
        if (result.isSuccess()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        OutputStream resBody = exchange.getResponseBody();
        String gsonString = gson.toJson(result);
        writeString(gsonString, resBody);
        resBody.close();
      }
    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      // may need to create and write new result to response body
      exchange.getResponseBody().close();
      e.printStackTrace();
    }

//    catch (DataAccessException e) {
//      e.printStackTrace();
//    }






//        // Get the output stream from the response
//        OutputStream os = exchange.getResponseBody();
//
//        // Serialize the response object into JSON and write it to the output stream
//        String jsonResponse = gson.toJson(response);
//        os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
//        os.close();
//      } else {
//        // If the request method is not POST, respond with a 405 Method Not Allowed status
//        exchange.sendResponseHeaders(405, -1);
//      }
//
//    }catch (IOException e){
//      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//      exchange.getRequestBody().close(); //close if doesn't work
//      e.printStackTrace();
//    }

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
