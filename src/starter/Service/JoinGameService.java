package Service;

import Request.JoinGameReq;
import Result.JoinGameResult;

public class JoinGameService {

  /**
   * Joins a game stored in the database, if the game exists
   * Checks for the user's teamColor and if there is none, they are an observer
   *
   * @param request The join game request to the database
   * @return JoinGameResult object with information on if the request was successful
   * */
  public JoinGameResult joinGame(JoinGameReq request){return null;}
}
