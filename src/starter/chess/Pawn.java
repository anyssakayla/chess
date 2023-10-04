package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Pawn extends ChessPieceImpl{


  public Pawn(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.PAWN);
  }


  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) { //possible moves
    Collection<ChessMove> possibleMoves=new HashSet<ChessMove>();

    if (teamColor == ChessGame.TeamColor.WHITE) {
      ChessPosition forwardOne=new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn());
      if (chessBoard.getPiece(forwardOne) == null) {
        ChessMove f1Move;
        if (forwardOne.getRow() == 7) { //check if it is at last row for promotion
          f1Move = new ChessMoveImpl(chessPosition, forwardOne, true); //promote true
        } else {
          f1Move = new ChessMoveImpl(chessPosition, forwardOne, false);
        }
        possibleMoves.add(f1Move); //add the move to possible moves
      }
      ChessPosition forwardTwo=new ChessPositionImpl(chessPosition.getRow() + 2, chessPosition.getColumn());
      if (chessPosition.getRow() == 1 && chessBoard.getPiece(forwardTwo) == null) { //if at row one, it is first turn
        ChessMove f2Move=new ChessMoveImpl(chessPosition, forwardTwo, false);
        possibleMoves.add(f2Move);
      }

      //checking for capturing
      ChessPosition diagonalRight = new ChessPositionImpl(chessPosition.getRow()+1, chessPosition.getColumn()+1);
      ChessPosition diagonalLeft = new ChessPositionImpl(chessPosition.getRow()+1, chessPosition.getColumn()-1);
      if(chessBoard.getPiece(diagonalLeft) != null && chessBoard.getPiece(diagonalLeft).getTeamColor() == ChessGame.TeamColor.BLACK)
      {
        //TODO: CHECK if pawn can be promoted!! if not, do this one
        ChessMove diagRMove = new ChessMoveImpl(chessPosition, diagonalRight, false);
      }


    }
    else{ //if piece is team Black, minus two or one instead of add

    }
    return possibleMoves;
  }
}//check for the end of the board
//for rook use a loop until stopping point (end of board or a piece in the way)
//check for not white for king