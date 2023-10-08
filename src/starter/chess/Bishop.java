package chess;

import java.util.Collection;
import java.util.HashSet;

public class Bishop extends ChessPieceImpl{
  public Bishop(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.BISHOP);
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) {
    Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

    for(int i = 0; i < 8; i++){ //increment to the right diagonal
      ChessPosition diagRight = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() + i);
      possibleMoves.add(checkMove(chessBoard, chessPosition, diagRight));
    }

    for(int i = 0; i < 8; i++){ //diagonal to the left
      ChessPosition diagLeft = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn() - i);
      possibleMoves.add(checkMove(chessBoard, chessPosition, diagLeft));
      //row will be incrementing by i as column will be decrementing by i
    }

    for(int i = 0; i < 8; i++){ //Diagonal down to the left
      ChessPosition downLeft = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() - i);
      possibleMoves.add(checkMove(chessBoard, chessPosition, downLeft));
    }

    for(int i = 0; i < 8; i++){ //Diagonal down to the right
      ChessPosition downRight = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn() + i);
      possibleMoves.add(checkMove(chessBoard, chessPosition, downRight));
    }

    return possibleMoves;
  }

  public ChessMove checkMove(ChessBoard chessBoard, ChessPosition chessPosition, ChessPosition endPos){

    if(endPos.getRow() >= 0 && endPos.getRow() < 8 && endPos.getColumn() >= 0 && endPos.getColumn() < 8){ //checking if end position is in bounds
      if(chessBoard.getPiece(endPos) == null){ //if the spot on the board is empty, move there
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, false); //create the move to add
        return addMove;
      }
      else if(chessBoard.getPiece(endPos).getTeamColor() != this.getTeamColor()){ //if there is a piece, but it is the other team, it is a valid move
        ChessMove addMove = new ChessMoveImpl(chessPosition, endPos, false); //create the move
        return addMove; //return the move
      }
    }
    return null; //if the move doesn't meet criteria, return null
  }
}
