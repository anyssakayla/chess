package chess;
import java.util.Collection;
import java.util.HashSet;

public class Rook extends ChessPieceImpl{

  public Rook(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.ROOK);
  }

  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) { //possible moves
    Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

    for(int i = 0; i < 8; i++){ //loop through all possible iterations of the length a rook can go
      ChessPosition forwardPos = new ChessPositionImpl(chessPosition.getRow() + i, chessPosition.getColumn()); //increment row by i
      if(forwardPos.getRow() < 8){ //make sure it is still in bounds
        if(chessBoard.getPiece(forwardPos) == null){
          ChessMove forwardOne = new ChessMoveImpl(chessPosition, forwardPos, null); //TODO: FALSE
          possibleMoves.add(forwardOne);
        }
        else if (chessBoard.getPiece(forwardPos).getTeamColor() != this.getTeamColor()){ //check if the team color is different
          ChessMove forwardOne = new ChessMoveImpl(chessPosition, forwardPos, null); //TODO: FALSE
          possibleMoves.add(forwardOne);
        }
      } //Rook iterates backwards to end of board
      ChessPosition backPos = new ChessPositionImpl(chessPosition.getRow() - i, chessPosition.getColumn()); //decrement by i
      if(backPos.getRow() >= 0){
        if(chessBoard.getPiece(backPos) == null){
          ChessMove backOne = new ChessMoveImpl(chessPosition, backPos, null); //TODO: FALSE
          possibleMoves.add(backOne); //if we pass in start, endposition, and possibleMoves, could make function to do this
        }
        else if(chessBoard.getPiece(backPos).getTeamColor() != this.getTeamColor()){
          ChessMove backOne = new ChessMoveImpl(chessPosition, backPos, null); //TODO: FALSE
          possibleMoves.add(backOne);
        }
      }//iterates to the right to end of board
      ChessPosition rightPos = new ChessPositionImpl(chessPosition.getRow(), chessPosition.getColumn() + i);
      if(rightPos.getColumn() < 8){
        if(chessBoard.getPiece(rightPos) == null || (chessBoard.getPiece(rightPos).getTeamColor() != this.getTeamColor())){
          ChessMove rightI = new ChessMoveImpl(chessPosition, rightPos, null); //TODO: FALSE
          possibleMoves.add(rightI);
        }
      }//iterates to the left and to the end of the board
      ChessPosition leftPos = new ChessPositionImpl(chessPosition.getRow(), chessPosition.getColumn() - i);
      if(rightPos.getColumn() >= 0){
        if(chessBoard.getPiece(leftPos) == null || (chessBoard.getPiece(leftPos).getTeamColor() != this.getTeamColor())){
          ChessMove leftI = new ChessMoveImpl(chessPosition, leftPos, null); //TODO: FALSE
          possibleMoves.add(leftI);
        }
      }
    }
    return possibleMoves;
  }
}
