package chess;

import java.util.Collection;
import java.util.HashSet;

public class Bishop extends ChessPieceImpl{
  public Bishop(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.BISHOP);
  }
  Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();
  boolean hasBeenCaptured = false;

  public Collection<ChessMove> returnMoves(){
    return possibleMoves;
  } //returns all moves in possibleMoves
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) {


    for(int i = 1; i < 8; i++){ //increment to the right diagonal, i starts at 1 since 0 won't increment anything
      ChessPosition diagRight = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() + i);

      if(checkMove(chessBoard, chessPosition, diagRight) != null) { //if there is a move to add, add the move
        possibleMoves.add(checkMove(chessBoard, chessPosition, diagRight));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else{ //if one move doesn't pass requirements, don't look for other ones past this spot
        break;
      }
    } //

    for(int i = 1; i < 8; i++){ //diagonal to the left
      ChessPosition diagLeft = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() - i);
      if(checkMove(chessBoard, chessPosition, diagLeft) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, diagLeft));
        if(hasBeenCaptured == true){
          hasBeenCaptured = false; //reset boolean and then break
          break;
        }
      }
      else{
        break;
      }
      //row will be incrementing by i as column will be decrementing by i
    }

    for(int i = 1; i < 8; i++){ //Diagonal down to the left
      ChessPosition downLeft = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() - i);
      if(checkMove(chessBoard, chessPosition, downLeft) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, downLeft));
        if(hasBeenCaptured == true){
          hasBeenCaptured = false;
          break;
        }
      }
      else {
        break; //if one move doesn't pass requirements, don't look for other ones past this spot
      }
    }

    for(int i = 1; i < 8; i++){ //Diagonal down to the right
      ChessPosition downRight = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() + i);
      if(checkMove(chessBoard, chessPosition, downRight) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, downRight));
        if(hasBeenCaptured == true){
          hasBeenCaptured = false;
          break;
        }
      }
      else{
        break;
      }
    }

    return possibleMoves;
  }

  public ChessMove checkMove(ChessBoard chessBoard, ChessPosition chessPosition, ChessPosition endPos){

    if(endPos.getRow() >= 0 && endPos.getRow() < 8 && endPos.getColumn() >= 0 && endPos.getColumn() < 8){ //checking if end position is in bounds
      if(chessBoard.getPiece(endPos) == null){ //if the spot on the board is empty, move there
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE//create the move to add
        return addMove;
      }
      else if(chessBoard.getPiece(endPos).getTeamColor() != this.getTeamColor()){ //if there is a piece, but it is the other team, it is a valid move
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE //create the move
        setCaptured(true);
        return addMove; //return the move //save the chessBoard.getPiece(endPos).getType? into something to show captured pieces?
      }
    }
    return null; //if the move doesn't meet criteria, return null
  }

  public void setCaptured(boolean captured){
    hasBeenCaptured = captured;
    //return captured;
  }
}
