import Handler.*;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Spark;
import spark.Request;
import spark.Response;
import Handler.CreateGameHandler;

public class Server {

  public static void main(String args[]){
    try {
      new CreateDB().establishDatabase();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
    Server.run();
  }


  public static void run() {
    // Specify the port you want the server to listen on
    Spark.port(8080);


    Spark.externalStaticFileLocation("web");
    Spark.init();

    Spark.post("/session", (request, response) -> (new LoginHandler()).handle(request, response));
    Spark.post("/user", (request, response) -> (new RegisterHandler()).handle(request, response));
    Spark.delete("/db", (request, response) -> (new ClearHandler()).handle(request, response));
    Spark.delete("/session", (request, response) -> (new LogoutHandler()).handle(request, response));
    Spark.post("/game", (request, response) -> (new CreateGameHandler()).handle(request, response));
    Spark.put("/game", (request, response) -> (new JoinGameHandler()).handle(request, response));
    Spark.get("/game", (request, response) -> (new ListGamesHandler()).handle(request, response));



  }
}
