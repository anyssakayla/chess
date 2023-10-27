package Service;
import chess.ChessGame;
import chess.Model.AuthToken;
import chess.Model.Game;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.UserDao;
import dataAccess.DAO.GameDao;
import Request.JoinGameReq;
import Result.JoinGameResult;
import dataAccess.DataAccessException;
import java.io.IOException;
import java.util.Locale;

public class JoinGameService {

  /**
   * Joins a game stored in the database, if the game exists
   * Checks for the user's teamColor and if there is none, they are an observer
   *
   * @param request The join game request to the database
   * @return JoinGameResult object with information on if the request was successful
   * */
  public JoinGameResult joinGame(JoinGameReq request) throws IOException, DataAccessException {
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();
    ChessGame.TeamColor teamColor;
    String requestAuth = request.getAuthToken();
    String requestColor = request.getTeamColor().toLowerCase(Locale.ROOT);
    JoinGameResult result = new JoinGameResult();

    authDao.getAuth(requestAuth); //make sure the request authToken is in the database
    Game joinGame = gameDao.findGame(request.getGameID()); //make sure the gameID is valid

    if(request.getTeamColor() == null){
      gameDao.claimSpot(request.getUsername(), request.getGameID(), null);
    }
     if(requestColor.equals("black")){ //if black, set teamcolor to black
       teamColor = ChessGame.TeamColor.BLACK;
       if(joinGame.getBlackUsername() == null){ //make sure black player doesn't already exist

         gameDao.claimSpot(request.getUsername(), request.getGameID(), teamColor);
       }
       else{
         result.setMessage("This spot is already taken");
         throw new IOException("Error: this spot is already taken");
       }
     }
     else if(requestColor.equals("white")){
       teamColor = ChessGame.TeamColor.WHITE;
       if(joinGame.getWhiteUsername() == null){
         gameDao.claimSpot(request.getUsername(), request.getGameID(), teamColor);
       }
       else{
         result.setMessage("This spot is already taken");
         throw new IOException("Error: this spot is already taken");
       }
     }
     else{
       result.setMessage("Error: bad request");
       throw new IOException("Error: bad request");
     }

     result.setGameID(request.getGameID()); //TODO: Do we need to set result's teamcolor?
     return result;
  }
}
