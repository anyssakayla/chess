package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Pawn extends ChessPieceImpl{


  public Pawn(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.PAWN);
  }

  public Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

  public Collection<ChessMove> returnMoves(ChessBoard chessBoard, ChessPosition chessPosition){
    pieceMoves(chessBoard, chessPosition); //have to call function to fill possibleMoves if any
    return possibleMoves;
  }
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) { //possible moves

    if (teamColor == ChessGame.TeamColor.WHITE) { //Team white
      ChessPosition forwardOne=new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn()); //create a position at forward one spot
      if (chessBoard.getPiece(forwardOne) == null) { //checks if the spot at forwardOne position is empty
        ChessMove f1Move;
        if (forwardOne.getRow() == 7) { //check if it is at last row for promotion
          //f1Move = new ChessMoveImpl(chessPosition, forwardOne, true); //promote true and create the forward1 move
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.BISHOP)); //add promotion pieces to possible moves
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.QUEEN));
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.KNIGHT));
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.ROOK));
        } else {
          f1Move=new ChessMoveImpl(chessPosition, forwardOne, false); //if can't be promoted, create a move where it is not promoted
          possibleMoves.add(f1Move); //add the move to possible moves
        }

      }
      ChessPosition forwardTwo=new ChessPositionImpl(chessPosition.getRow() + 2, chessPosition.getColumn());
      if (chessPosition.getRow() == 1 && chessBoard.getPiece(forwardTwo) == null) { //if at row one, it is first turn
        ChessMove f2Move=new ChessMoveImpl(chessPosition, forwardTwo, false);
        possibleMoves.add(f2Move);
      }

      //checking for capturing diagonally
      ChessPosition diagonalRight=new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn() + 1);
      ChessPosition diagonalLeft=new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn() - 1);
      //  Diagonal to the left
      //TODO: FIX OUT OF BOUNDS ERROR, NEED TO MAKE SURE >0 AND <8
      if (diagonalLeft.getRow() >= 0 && diagonalLeft.getRow() < 8 && diagonalLeft.getColumn() >= 0 && diagonalLeft.getColumn() < 8) {
        if (chessBoard.getPiece(diagonalLeft) != null && chessBoard.getPiece(diagonalLeft).getTeamColor() == ChessGame.TeamColor.BLACK) {
          ChessMove diagLMove;

          if (diagonalLeft.getRow() == 7) {
            // diagLMove = new ChessMoveImpl(chessPosition, diagonalLeft, true);
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.BISHOP));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.QUEEN));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.KNIGHT));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.ROOK));
          } else {
            diagLMove=new ChessMoveImpl(chessPosition, diagonalLeft, false);
            possibleMoves.add(diagLMove); //add the diagonal left move to possible moves
          }
        }
      }
      //  Diagonal to the right
      if (diagonalRight.getRow() >= 0 && diagonalRight.getRow() < 8 && diagonalRight.getColumn() >= 0 && diagonalRight.getColumn() < 8) {
        if (chessBoard.getPiece(diagonalRight) != null && chessBoard.getPiece(diagonalRight).getTeamColor() == ChessGame.TeamColor.BLACK) {
          ChessMove diagRMove; //create a diagonal right move

          if (diagonalRight.getRow() == 7) { //if at end of board, promote
            // diagRMove = new ChessMoveImpl(chessPosition, diagonalRight, true); //set the diagonal right move and promotion
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.BISHOP)); //create promotion pieces as possible move
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.QUEEN));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.KNIGHT));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.ROOK));
          } else {
            diagRMove=new ChessMoveImpl(chessPosition, diagonalRight, false);
            possibleMoves.add(diagRMove); //add the diagonal left move to possible moves
          }
        }
      }
    }
    //TODO: Team Black starts here
    else{ //if piece is team Black, minus two or one instead of add
      //  Everything is decremented instead of incremented since we are going top-down instead of down-up
      ChessPosition forwardOne = new ChessPositionImpl(chessPosition.getRow() - 1, chessPosition.getColumn()); //create a position at forward one spot
      if (chessBoard.getPiece(forwardOne) == null) { //checks if the spot at forwardOne position is empty
        ChessMove f1Move;
        if (forwardOne.getRow() == 0) { //check if it is at last row for promotion
        //  f1Move = new ChessMoveImpl(chessPosition, forwardOne, true); //promote true and create the forward1 move
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.BISHOP));
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.QUEEN));
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.KNIGHT));
          possibleMoves.add(new ChessMoveImpl(chessPosition, forwardOne, PieceType.ROOK));
        } else {
          f1Move = new ChessMoveImpl(chessPosition, forwardOne, false); //if can't be promoted, create a move where it is not promoted
          possibleMoves.add(f1Move); //add the move to possible moves
        }
      }
      ChessPosition forwardTwo=new ChessPositionImpl(chessPosition.getRow() - 2, chessPosition.getColumn());
      if (chessPosition.getRow() == 6 && chessBoard.getPiece(forwardTwo) == null && chessBoard.getPiece(forwardOne) == null) { //if at row 6, it is first turn
        ChessMove f2Move = new ChessMoveImpl(chessPosition, forwardTwo, false);
        possibleMoves.add(f2Move);
      }

      //checking for capturing diagonally
      ChessPosition diagonalRight = new ChessPositionImpl(chessPosition.getRow()-1, chessPosition.getColumn()-1);
      ChessPosition diagonalLeft = new ChessPositionImpl(chessPosition.getRow()-1, chessPosition.getColumn()+1);
      //  Diagonal to the left
      if(diagonalLeft.getRow() >= 0 && diagonalLeft.getRow() < 8 && diagonalLeft.getColumn() >= 0 && diagonalLeft.getColumn() < 8) {
        if (chessBoard.getPiece(diagonalLeft) != null && chessBoard.getPiece(diagonalLeft).getTeamColor() == ChessGame.TeamColor.WHITE) {
          ChessMove diagLMove;

          if (diagonalLeft.getRow() == 0) {
            //   diagLMove = new ChessMoveImpl(chessPosition, diagonalLeft, true);
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.BISHOP));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.QUEEN));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.KNIGHT));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalLeft, PieceType.ROOK));
          } else {
            diagLMove=new ChessMoveImpl(chessPosition, diagonalLeft, false);
            possibleMoves.add(diagLMove); //add the diagonal left move to possible moves
          }
        }
      }
      //  Diagonal to the right
      if(diagonalRight.getRow() >= 0 && diagonalRight.getRow() < 8 && diagonalRight.getColumn() >= 0 && diagonalRight.getColumn() < 8) {
        if (chessBoard.getPiece(diagonalRight) != null && chessBoard.getPiece(diagonalRight).getTeamColor() == ChessGame.TeamColor.WHITE) {
          ChessMove diagRMove; //create a diagonal right move

          if (diagonalRight.getRow() == 0) { //if at end of board, promote
            // diagRMove = new ChessMoveImpl(chessPosition, diagonalRight, true); //set the diagonal right move and promotion
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.BISHOP));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.QUEEN));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.KNIGHT));
            possibleMoves.add(new ChessMoveImpl(chessPosition, diagonalRight, PieceType.ROOK));
          } else {
            diagRMove=new ChessMoveImpl(chessPosition, diagonalRight, false);
            possibleMoves.add(diagRMove); //add the diagonal left move to possible moves
          }
        }
      }

    }
    return possibleMoves;
  }

}//check for the end of the board

