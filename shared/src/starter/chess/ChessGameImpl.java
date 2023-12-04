package chess;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame{
  public ChessBoard chessBoard = new ChessBoardImpl();
  public TeamColor teamColor;

  public ChessGameImpl(){

  }
  //example shown in class
//  public static ChessGameImpl create(String serializer){
//    var x = new Gson().fromJson(serializedGame, ChessGameImpl.class);
//
//    GsonBuilder gsonBuilder = new GsonBuilder();
//    gsonBuilder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
//    gsonBuilder.registerTypeAdapter(ChessGameImpl.class, new ChessGameAdapter());
//    x = gsonBuilder.create().fromJson(serializedGame, ChessGameImpl.class);
//
//    return x;
//  }
  public static class ChessGameAdapter implements JsonDeserializer<ChessGame> {
    public ChessGame deserialize(JsonElement element, Type type, JsonDeserializationContext context){
      GsonBuilder builder = new GsonBuilder();
      //builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
      var serializer = builder.create();
      return serializer.fromJson(element, ChessGameImpl.class);
    }
  }

  @Override
  public TeamColor getTeamTurn() {
    return teamColor;
  }

  @Override
  public void setTeamTurn(TeamColor team) {
    teamColor = team;
  }

  @Override
  public Collection<ChessMove> validMoves(ChessPosition startPosition) {

    ChessPiece currPiece = chessBoard.getPiece(startPosition); //set current piece to the starting position
    Collection<ChessMove> possMoves = currPiece.pieceMoves(chessBoard, startPosition); //all possible moves
    Collection<ChessMove> legalMoves = new HashSet<ChessMove>(); //only moves that are legal

    ChessMove tempMove = new ChessMoveImpl(); //temp move will be used to make a move and check if it is valid for inCheck
    ChessPiece capture; //can get captured
    if(possMoves != null && possMoves.size() != 0) { //if it is not empty, iterate through
      for (ChessMove chessMove : possMoves) { //TODO: DELETE THIS, TRYING TO ITERATE THROUGH SOMETHING THAT IS NULL
        tempMove.setStartingPos(chessMove.getEndPosition()); //temp move will unto the move for checking
        tempMove.setEndingPos(chessMove.getStartPosition());
        tempMove.setPromotion(currPiece.getPieceType());

        capture=chessBoard.getPiece(chessMove.getEndPosition()); //pass in the piece that is captured
        chessBoard.completesMove(chessMove); //complete move and check if it is in check
        if (!isInCheck(currPiece.getTeamColor())) { //if it is not in check, add the move to legal moves
          legalMoves.add(chessMove); //TODO: SHOULD ISINCHECK RESET POSSIBLEMOVES?
        }
        chessBoard.completesMove(tempMove); //undo the move with the temporary move and set it back
        chessBoard.addPiece(chessMove.getEndPosition(), capture);
      }
    }
    return legalMoves;
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    if(this.teamColor != null){
      if(chessBoard.getPiece(move.getStartPosition()).getTeamColor() != this.teamColor){
        throw new InvalidMoveException("This piece does not belong to the playing team");
      }
    }
//    if(validMoves(move.getStartPosition()).isEmpty()){ //if validMoves is empty, then there are no moves to make
//      throw new InvalidMoveException("This move is not valid for the indicated piece");
//    }
    Collection<ChessMove> legalMoves = validMoves(move.getStartPosition()); //put all valid moves into a collection of legal moves
    if(!legalMoves.contains(move)){ //if the move is not legal, throw exception
      throw new InvalidMoveException("This move is not valid for the indicated piece");
    }
    chessBoard.completesMove(move);
    if(teamColor == TeamColor.WHITE){
      setTeamTurn(TeamColor.BLACK);
    }
    else{
      setTeamTurn(TeamColor.WHITE);
    }

  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    ChessPosition kingPos = new ChessPositionImpl(0, 0);
    //TODO: Change kingPos declaration to be at 0,0, as it may not be there, set to null?
    for(int i = 0; i < 8; i ++){ //gets the king's position for the indicated team
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getPieceType() == ChessPiece.PieceType.KING){ //if the piece at the position is a king
            if(chessBoard.getPiece(position).getTeamColor() == teamColor){ //if it is equal to this team's color, it is the king we are looking for
              kingPos.setRow(i);
              kingPos.setCollumn(j);
              break; //break from loop if already found the king
            }
          }
        }
      }
    }
    for(int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        position.setRow(i); //set the row anc column to i and j
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null) { //if there is a piece at position
//          if (chessBoard.getPiece(position).getPieceType() == ChessPiece.PieceType.KING) {
            if (chessBoard.getPiece(position).getTeamColor() != teamColor) { //if piece is from opposite team
              teamMoves=chessBoard.getPiece(position).pieceMoves(chessBoard, position); //call the piece's pieceMoves
              for (ChessMove move : teamMoves) {
                if (move.getEndPosition().equals(kingPos)) { //if they can have a move that equals the king's position
                  return true;
                }
//              else {
//                return false;
//              }
              }
//            }
          }
        }
      }
    }
    return false; //if it does not meet criteria for being in check, return false
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    if(!isInCheck(teamColor)){
      return false;
    }
    for(int i = 0 ; i < 8; i++){
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);

        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getTeamColor() == teamColor){
            teamMoves = validMoves(position);
            if(!teamMoves.isEmpty()){
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) { //checks if a team has any legal moves available
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    if(isInCheck(teamColor)){
      return false;
    }

    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getTeamColor() == teamColor){
            teamMoves = validMoves(position);
            if(!teamMoves.isEmpty()){ //if it contains moves, it is not in stalemate
              return false;
            }
          }
        }
      }
    }
    return true; //if it doesn't meet conditions, then they are in stalemate
  }

  @Override
  public void setBoard(ChessBoard board) {
    if(board.getClass() == chessBoard.getClass()){
      chessBoard = board;
    }
  }

  @Override
  public ChessBoard getBoard() {
    return chessBoard;
  }
}
