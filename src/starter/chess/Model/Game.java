package chess.Model;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import chess.*;
import com.google.gson.*;
import com.google.protobuf.Type;
import dataAccess.DAO.GameDao;

import static chess.ChessPiece.PieceType.PAWN;

public class Game {
  int gameID;
  String whiteUsername;
  String blackUsername;
  String gameName;
  ChessGame game = new ChessGameImpl();
  private Collection<String> gameObserver = new HashSet<String>();

  /**
   * Represents a ChessGame ID
   *
   * @param gameID Represents a unique ID number that is associated to a game
   * @param gameName unique name that represents a chess game
   * */
  public Game(int gameID, String gameName){
    this.gameID = gameID;
    this.gameName = gameName;
  }

  public Game() {}

  public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGameImpl game){
    this.gameID = gameID;
    this.whiteUsername = whiteUsername;
    this.blackUsername = blackUsername;
    this.gameName = gameName;
    this.game = game;
  }

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID = gameID;
  }

  public String getWhiteUsername() {
    return whiteUsername;
  }

  public void setWhiteUsername(String whiteUsername) {
    this.whiteUsername=whiteUsername;
  }

  public String getBlackUsername() {
    return blackUsername;
  }

  public void setBlackUsername(String blackUsername) {
    this.blackUsername = blackUsername;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }

  public ChessGame getGame() {
    return game;
  }

  public void setGame(ChessGameImpl game) {
    this.game = game;
  }

  public Collection<String> getGameObserver() {
    return gameObserver;
  }

  public void addGameObserver(String username){
    gameObserver.add(username);
  }

  public void removeGameObserver(String username){
    gameObserver.remove(username);
  }

  public void setGameObserver(Collection<String> gameObserver) {
    this.gameObserver = gameObserver;
  }

  @Override
  public boolean equals(Object obj) {
    Game game1 = (Game) obj;
    boolean gameEquals = true;
    if (this == obj) return true;
    if(obj == null && this != null){
      return false;
    }
    if(obj != null && this == null){
      return false;
    }
    if(!(game1.getGameName().equals(this.getGameName()))){
      return false;
    }

    if((game1.getGameID() != this.getGameID())){
      return false;
    }

    if(game1.getBlackUsername() != this.getBlackUsername()){
      return false;
    }
    if(game1.getWhiteUsername() != this.getWhiteUsername()){
      return false;
    }
    if(!(game1.gameObserver.equals(this.gameObserver))){
      return false;
    }

    return gameEquals;
  }

  public static Gson gsonSerializer(){
    GsonBuilder gB = new GsonBuilder();
    gB.registerTypeAdapter(Game.class, new ChessGameAdapter());
    return gB.create();
  }

  public static class ChessGameAdapter implements JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(ChessBoardImpl.class, new ChessBoardAdapter());
      var serializer = gsonBuilder.create();
      return serializer.fromJson(jsonElement, Game.class);
    }
  }

  public static class ChessBoardAdapter implements JsonDeserializer<ChessBoardImpl> {

    @Override
    public ChessBoardImpl deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      GsonBuilder gsonBuilder=new GsonBuilder();
      gsonBuilder.registerTypeAdapter(ChessPieceImpl.class, new ChessPieceAdapter());
      var serializer=gsonBuilder.create();
      return serializer.fromJson(jsonElement, ChessBoardImpl.class);
    }
  }



  public static class ChessPieceAdapter implements JsonDeserializer<ChessPieceImpl> {

    @Override
    public ChessPieceImpl deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      var newPiece=new Gson().fromJson(jsonElement, ChessPieceImpl.class);
      var pieceType=newPiece.getPieceType();
      var pieceColor=newPiece.getTeamColor();
      switch (pieceType) {
        case PAWN:
          return new Pawn(pieceColor);
        //break;
        case ROOK:
          return new Rook(pieceColor);
        //break;
        case KNIGHT:
          return new Knight(pieceColor);
        //break;
        case BISHOP:
          return new Bishop(pieceColor);
        //break;
        case QUEEN:
          return new Queen(pieceColor);
        //break;
        case KING:
          return new King(pieceColor);
        //break;
      }
      return new ChessPieceImpl(pieceColor, pieceType);
    }
  }


  }

//  @Override
//  public int hashCode() {
//    return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game, gameObserver);
//  }

