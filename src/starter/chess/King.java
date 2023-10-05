package chess;

import java.util.Collection;
import java.util.HashSet;

public class King extends ChessPieceImpl{
  public King(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.KING);
  }

  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) { //possible moves
    Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

    if (teamColor == ChessGame.TeamColor.WHITE) {
      ChessPosition forwardOne = new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn()); //create a position at forward one spot
      whiteKing(possibleMoves, chessBoard, chessPosition, forwardOne); //moving the white king forward spot

      ChessPosition rightCorner = new ChessPositionImpl(chessPosition.getRow() + 1,chessPosition.getColumn() + 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, rightCorner);

      ChessPosition leftCorner = new ChessPositionImpl(chessPosition.getRow() + 1,chessPosition.getColumn() - 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, leftCorner);

      ChessPosition right = new ChessPositionImpl(chessPosition.getRow(),chessPosition.getColumn() + 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, right);

      ChessPosition left = new ChessPositionImpl(chessPosition.getRow(),chessPosition.getColumn() - 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, left);

      ChessPosition backOne = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn()); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, backOne);

      ChessPosition bottomLCorner = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn() - 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, bottomLCorner);

      ChessPosition bottomRCorner = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn() + 1); //right corner
      whiteKing(possibleMoves, chessBoard, chessPosition, bottomRCorner);
    }
    else{ //does the same thing but with the black pieces and checks for capturing white pieces
      ChessPosition forwardOne = new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn()); //create a position at forward one spot
      blackKing(possibleMoves, chessBoard, chessPosition, forwardOne); //moving the white king forward spot

      ChessPosition rightCorner = new ChessPositionImpl(chessPosition.getRow() + 1,chessPosition.getColumn() + 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, rightCorner);

      ChessPosition leftCorner = new ChessPositionImpl(chessPosition.getRow() + 1,chessPosition.getColumn() - 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, leftCorner);

      ChessPosition right = new ChessPositionImpl(chessPosition.getRow(),chessPosition.getColumn() + 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, right);

      ChessPosition left = new ChessPositionImpl(chessPosition.getRow(),chessPosition.getColumn() - 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, left);

      ChessPosition backOne = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn()); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, backOne);

      ChessPosition bottomLCorner = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn() - 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, bottomLCorner);

      ChessPosition bottomRCorner = new ChessPositionImpl(chessPosition.getRow() - 1,chessPosition.getColumn() + 1); //right corner
      blackKing(possibleMoves, chessBoard, chessPosition, bottomRCorner);
    }
    return possibleMoves;
  }

  public void whiteKing(Collection<ChessMove> possibleMoves, ChessBoard chessBoard, ChessPosition startingPos, ChessPosition endingPos){
    if((endingPos.getRow() < 8 && endingPos.getRow() >= 0) && (endingPos.getColumn() < 8 && endingPos.getColumn() >= 0)){ //check that the ending position is in bounds of board
      ChessMove newMove = new ChessMoveImpl(startingPos, endingPos, false); //create a move from starting and ending positions
      if(chessBoard.getPiece(endingPos) == null){
        possibleMoves.add(newMove); //add the new move to possibleMoves
      }
      else if(chessBoard.getPiece(endingPos).getTeamColor() == ChessGame.TeamColor.BLACK){
        possibleMoves.add(newMove);
        //say that the piece gets captured?
      }
    }
  }

  public void blackKing(Collection<ChessMove> possibleMoves, ChessBoard chessBoard, ChessPosition startingPos, ChessPosition endingPos){
    if((endingPos.getRow() < 8 && endingPos.getRow() >= 0) && (endingPos.getColumn() < 8 && endingPos.getColumn() >= 0)){ //check that the ending position is in bounds of board
      ChessMove newMove = new ChessMoveImpl(startingPos, endingPos, false); //create a move from starting and ending positions
      if(chessBoard.getPiece(endingPos) == null){
        possibleMoves.add(newMove); //add the new move to possibleMoves
      }
      else if(chessBoard.getPiece(endingPos).getTeamColor() == ChessGame.TeamColor.WHITE){
        possibleMoves.add(newMove);
        //say that the piece gets captured?
      }
    }
  }
}
