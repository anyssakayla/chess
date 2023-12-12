package ui;
import java.io.*;
import java.net.HttpURLConnection;
import Model.AuthToken;
import Model.Game;
import chess.ChessBoard;
import Model.User;
import Result.*;
import Request.*;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.net.URL;
import java.net.URI;

public class ServerFacade {

  private  String serverUrl;
  private int serverResponse; //for the response code

  public int getServerResponse(){return serverResponse;}
  public ServerFacade(String serverUrl){
    this.serverUrl = serverUrl;
  }

  public void clear()  {
    try {
      var object = createReq("DELETE", "/db", null, null);
      if(object == null){ //if it is null, then something went wrong
        System.out.println("Please try again");
      }
      else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          System.out.println("The Database has been cleared");
        }
        else if(statusCode == 401){
          System.out.println("Unauthorized. Please try again");
        }
        else{
          System.out.println("Error occurred, please try again");
        }
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public String login(String username, String password){
    LoginReq loginReq = new LoginReq(username, password);
   // var requestObj = Map.of("username", username, "password", password);
    try {
      var object = createReq("POST", "/session", loginReq, null);
      if(object == null){
        return null;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          var objUser = object.get("username").getAsString(); //make sure that it is getting username from object
          var token = object.get("authToken").getAsString();
          System.out.println(username + " is now logged in \n");
         // System.out.println(token);
          return token;
        }else if(statusCode == 401){
          System.out.println("Unauthorized, please try again \n");
        }
        else{
          System.out.println("Error occurred, \n");
          return null;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    //have to create this return statement because the try/catch block
    String somethingWentWrong = null; //the code shouldn't reach this point unless something went wrong
    return somethingWentWrong;
  }

  public String register(String username, String password, String email){
    RegisterReq registerReq = new RegisterReq(username, password, email);
    //var requestObj = Map.of("username", username, "password", password, "email", email);

    try {
      var object = createReq("POST", "/user", registerReq, null);
      if(object == null){
        return null;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          var token = object.get("authToken").getAsString();
          System.out.println("You are now logged in as " + username);
          return token;
        }
        else if(statusCode == 400){ //if error, return null
          System.out.println("Bad request. Please try again \n");
          return null;
        }
        else if(statusCode == 403){
          System.out.println("This user is already taken. Please try again \n");
          return null;
        }
        else{
          System.out.println("Error occurred. Please try again \n");
          return null;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null; //some other error occurred
  }

  public boolean logout (String authToken) {
    JsonObject object =null;
    try {
      object = createReq("DELETE", "/session", null, authToken);
      if(object == null){
        return false;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          System.out.println("You are now logged out \n");
          return true;
        }else if(statusCode == 401){
          System.out.println("Unauthorized. Please try again \n");
        }else{
          System.out.println("Error occurred. Please try again \n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public String createGame(String authToken, String gameName){
    CreateGameReq createGameReq = new CreateGameReq(gameName, authToken);
   // var requestObj = Map.of("gameName", gameName);
    try {
      var object = createReq("POST", "/game", createGameReq, authToken);
      if(object == null){
        return null;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          var gameID = object.get("gameID").getAsString();
          System.out.println("Game "+ gameName + " was created with the Game ID: " + gameID);
          return gameID;
        }else if(statusCode == 400){
          System.out.println("Bad request. Please try again \n");
          return null;
        }else if(statusCode == 401){
          System.out.println("Unauthorized. Please try again \n");
          return null;
        }else {
          System.out.println("Error occurred. Please try again \n");
          return null;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null; //in order to reach this line, some other error must have occurred
  }


  public String list(String authToken){
    try {
      ListGamesReq listGamesReq = new ListGamesReq(authToken);
      var object = createReq("GET", "/game", null, authToken);
      if(object == null){
        return null;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){
          var gamesToList = (JsonArray) object.get("games");
          System.out.println("Here are the current games: ");
          for(JsonElement element : gamesToList){
            var jsObject = element.getAsJsonObject();
            String gameName = jsObject.get("gameName").getAsString();
            String gameID = jsObject.get("gameID").getAsString();
            String whiiteUser;
            String blackUser;

            if(jsObject.has("whiteUsername")){
              whiiteUser = jsObject.get("whiteUsername").getAsString();
            }else{
              whiiteUser = "null";
            }
            if(jsObject.has("blackUsername")){
              blackUser = jsObject.get("blackUsername").getAsString();
            }else{
              blackUser = "null";
            }
            System.out.println("GameName: " + gameName);
            System.out.println("GameID: " + gameID);
            System.out.println("White Username: " + whiiteUser);
            System.out.println("Black Username: " + blackUser);
          }
          return " ";
        }else if(statusCode == 400){
          System.out.println("Bad request. Please try again");
          return null;
        }else if(statusCode == 401){
          System.out.println("Unauthorized. Please try again");
          return null;
        }else{
          System.out.println("Error occured. Please try again");
          return null;
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean joinGame(String gameID, String playerColor, String authToken){
    Integer ID = Integer.valueOf(gameID);
   // JoinGameReq joinGameReq = new JoinGameReq(playerColor, ID);
    var requestObj = Map.of("gameID", gameID); //i need to declare it like this?
    if(playerColor != null){

      requestObj = Map.of("gameID", gameID, "playerColor", playerColor.toUpperCase(Locale.ROOT));
    }
    try {
      var object = createReq("PUT", "/game", requestObj, authToken);
      if(object == null){
        System.out.println("Error occurred. Please try again");
        return false;
      }else{
        var statusCode = object.get("status").getAsInt();
        if(statusCode == 200){ //success
          System.out.println("Successfully joined the game \n");
          return true;
        }else if(statusCode == 400){
          System.out.println("Bad request. Please try again");
          return false;
        }else if(statusCode == 401){
          System.out.println("Unauthorized. Please try again");
          return false;
        }else{
          System.out.println("Error occurred. Please try again \n");
          return false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }



  private JsonObject createReq(String method, String path, Object request, String authToken) throws IOException {
    try{

      URL urlAddition = (new URI(serverUrl + path)).toURL(); //add the path to the Url
      HttpURLConnection httpURLConnection = (HttpURLConnection) urlAddition.openConnection();
      httpURLConnection.setRequestMethod(method);
      httpURLConnection.setDoOutput(true);

      if(authToken != null){
        httpURLConnection.addRequestProperty("Authorization", authToken);
        httpURLConnection.setRequestProperty("Authorization", authToken);
      }
      if(request!= null){
        httpURLConnection.addRequestProperty("Accept", "application/json"); //should this be setRequestProp?
        String rData = new Gson().toJson(request);
        try(OutputStream rBody = httpURLConnection.getOutputStream()){
          rBody.write(rData.getBytes());
        }
      }

      httpURLConnection.connect();
      var status = httpURLConnection.getResponseCode();
      if(status == 200){
        JsonObject jsonObject = null;
        if(httpURLConnection.getContentLength() < 0){
          try(InputStream inputStream = httpURLConnection.getInputStream()){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
              StringBuilder stringBuilder = new StringBuilder();

              String lineRead;
              while((lineRead = bufferedReader.readLine()) != null){
                stringBuilder.append(lineRead);
              }
              jsonObject = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
              jsonObject.add("status", new JsonPrimitive(status));
              httpURLConnection.disconnect();
              return jsonObject;
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }else{
        var jsonObject = new JsonObject();
        jsonObject.add("status", new JsonPrimitive(status));
        httpURLConnection.disconnect();
        return jsonObject;
      }
      } catch (URISyntaxException ex) {
      System.out.println(ex);
      ex.printStackTrace();
      return null;
    }
    JsonObject somethingWentWrong = null; //this should be an unreachable statement
    return somethingWentWrong;

  }





  }
