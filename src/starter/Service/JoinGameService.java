package Service;
import chess.ChessGame;
import Model.Game;
import dataAccess.DAO.AuthDao;
import dataAccess.DAO.GameDao;
import Request.JoinGameReq;
import Result.JoinGameResult;
import dataAccess.DataAccessException;

import java.util.Locale;

public class JoinGameService {

  /**
   * Joins a game stored in the database, if the game exists
   * Checks for the user's teamColor and if there is none, they are an observer
   *
   * @param request The join game request to the database
   * @return JoinGameResult object with information on if the request was successful
   * */
  public JoinGameResult joinGame(JoinGameReq request, String auth) throws DataAccessException {
    AuthDao authDao = new AuthDao();
    GameDao gameDao = new GameDao();
    ChessGame.TeamColor teamColor;
    JoinGameResult result = new JoinGameResult();
    String requestColor = request.getPlayerColor();
    String notNullColor = "test";

    Game joinGame = gameDao.findGame(request.getGameID()); //make sure the gameID is valid

    if(joinGame == null){
      result.setMessage("Error: bad request");
      return result;
    }

    if(authDao.getAuth(auth) == null){
      result.setMessage("Error: unauthorized");
      return result;
    }

    if(request.getPlayerColor() != null){ //if it is null, it will be an error to do lowercase
      notNullColor = request.getPlayerColor().toLowerCase(Locale.ROOT);
    }

    if(requestColor == null){
      gameDao.claimSpot(authDao.getAuth(auth).getUsername(), request.getGameID(), null);
      result.setTeamColor(null);
      result.setUsername(authDao.getAuth(auth).getUsername());
      result.setGameID(request.getGameID());
    }

     else if(notNullColor.equals("black")){ //if black, set teamcolor to black
       teamColor = ChessGame.TeamColor.BLACK;
       if(joinGame.getBlackUsername() == null){ //make sure black player doesn't already exist
         result.setTeamColor("black");
         result.setUsername(authDao.getAuth(auth).getUsername());
         result.setGameID(request.getGameID());
         gameDao.claimSpot(authDao.getAuth(auth).getUsername(), request.getGameID(), teamColor);
       }
       else{
         result.setMessage("Error: already taken");
         //throw new IOException("Error: already taken");
       }
     }
     else if(notNullColor.equals("white")){
       teamColor = ChessGame.TeamColor.WHITE;
       if(joinGame.getWhiteUsername() == null){
         result.setTeamColor("white");
         result.setUsername(authDao.getAuth(auth).getUsername());
         result.setGameID(request.getGameID());
         gameDao.claimSpot(authDao.getAuth(auth).getUsername(), request.getGameID(), teamColor);
       }
       else{
         result.setMessage("Error: already taken");
         //throw new IOException("Error: this spot is already taken");
       }
     }
     else{
       result.setMessage("Error: bad request");
      // throw new IOException("Error: bad request");
     }

     result.setGameID(request.getGameID());
     return result;
  }
}
