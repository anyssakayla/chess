package chess;

import java.util.Collection;
import java.util.HashSet;

public class Queen extends ChessPieceImpl{
  public Queen(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.QUEEN);
  }
  Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();
  boolean hasBeenCaptured = false;

  public Collection<ChessMove> returnMoves(){
    return possibleMoves;
  }
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) {


    //Diagonal Moves
    for(int i = 1; i < 8; i++){ //increment to the right diagonal, i starts at 1 since 0 won't increment anything
      ChessPosition diagRight = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() + i); //creates a position to be checked for a possible Move

      if(checkMove(chessBoard, chessPosition, diagRight) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, diagRight)); //adds the move that is checked by the checkMove function
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else { //if one move doesn't pass requirements, don't look for other ones past this spot
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //diagonal to the left
      ChessPosition diagLeft = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() - i); //row will be incrementing by i as column will be decrementing by i
      if(checkMove(chessBoard, chessPosition, diagLeft) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, diagLeft));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //Diagonal down to the left
      ChessPosition downLeft = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() - i);
      if(checkMove(chessBoard, chessPosition, downLeft) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, downLeft));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //Diagonal down to the right
      ChessPosition downRight = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() + i);
      if(checkMove(chessBoard, chessPosition, downRight) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, downRight));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }
    //Straight line moves, rook moves
    for(int i = 1; i < 8; i++){ //Straight up
      ChessPosition straightUp = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn()); //column stays the same, but row increments up
      if(checkMove(chessBoard, chessPosition, straightUp) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, straightUp));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //Straight right
      ChessPosition straightRight = new ChessPositionImpl(chessPosition.getRow(), chessPosition.getColumn() + i);
      if(checkMove(chessBoard, chessPosition, straightRight) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, straightRight));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //Straight left
      ChessPosition straightLeft = new ChessPositionImpl(chessPosition.getRow(), chessPosition.getColumn() - i);
      if(checkMove(chessBoard, chessPosition, straightLeft) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, straightLeft));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }

    for(int i = 1; i < 8; i++){ //Straight down
      ChessPosition straightDown = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn());
      if(checkMove(chessBoard, chessPosition, straightDown) != null) {
        possibleMoves.add(checkMove(chessBoard, chessPosition, straightDown));
        if(hasBeenCaptured == true){ //if one of the moves captures a piece, you can't go any further than that
          hasBeenCaptured = false; //reset the boolean for the next diagonal iteration
          break; //there are no more possible moves, so break out of loop
        }
      }
      else {
        break;
      }
    }
    return possibleMoves;
  }

  public ChessMove checkMove(ChessBoard chessBoard, ChessPosition chessPosition, ChessPosition endPos){

    if(endPos.getRow() >= 0 && endPos.getRow() < 8 && endPos.getColumn() >= 0 && endPos.getColumn() < 8){ //checking if end position is in bounds
      if(chessBoard.getPiece(endPos) == null){ //if the spot on the board is empty, move there
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE BACK//create the move to add
        return addMove;
      }
      else if(chessBoard.getPiece(endPos).getTeamColor() != this.getTeamColor()){ //if there is a piece, but it is the other team, it is a valid move
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE //create the move
        setCaptured(true);
        return addMove; //return the move
      }
    }
    return null; //if the move doesn't meet criteria, return null
  }

  public void setCaptured(boolean captured){ //used to see if a piece has been captured within the added moves
    hasBeenCaptured = captured;
    //return captured;
  }
}
