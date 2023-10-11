package chess;
import java.util.Collection;
import java.util.HashSet;

public class Knight extends ChessPieceImpl{
  public Knight(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.KNIGHT);
  }
  Collection<ChessMove> possibleMoves = new HashSet<ChessMove>();

  public Collection<ChessMove> returnMoves(ChessBoard chessBoard, ChessPosition chessPosition){
    pieceMoves(chessBoard, chessPosition);
    return possibleMoves;
  } //returns all moves in possibleMoves
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition) {

    ChessPosition upLeft = new ChessPositionImpl(chessPosition.getRow() + 2, chessPosition.getColumn() - 1);
    if(addingMoves(chessBoard, chessPosition, upLeft) != null){ //needs to check if it is null, so it doesn't add null elements to possibleMoves
      possibleMoves.add(addingMoves(chessBoard, chessPosition, upLeft));
    }

    ChessPosition upRight = new ChessPositionImpl(chessPosition.getRow() + 2, chessPosition.getColumn() + 1);
    if(addingMoves(chessBoard, chessPosition, upRight) != null){
      possibleMoves.add(addingMoves(chessBoard, chessPosition, upRight));
    }

    ChessPosition leftLeft = new ChessPositionImpl(chessPosition.getRow() - 1, chessPosition.getColumn() - 2);
    if(addingMoves(chessBoard, chessPosition, leftLeft) != null){
      possibleMoves.add(addingMoves(chessBoard, chessPosition, leftLeft));
    }

    ChessPosition leftRight = new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn() - 2);
    if(addingMoves(chessBoard, chessPosition, leftRight) != null) {
      possibleMoves.add(addingMoves(chessBoard, chessPosition, leftRight));
    }

    ChessPosition downLeft = new ChessPositionImpl(chessPosition.getRow() - 2, chessPosition.getColumn() - 1);
    if(addingMoves(chessBoard, chessPosition, downLeft) != null) {
      possibleMoves.add(addingMoves(chessBoard, chessPosition, downLeft));
    }

    ChessPosition downRight = new ChessPositionImpl(chessPosition.getRow() - 2, chessPosition.getColumn() + 1);
    if(addingMoves(chessBoard, chessPosition, downRight) != null) {
      possibleMoves.add(addingMoves(chessBoard, chessPosition, downRight));
    }

    ChessPosition rightLeft = new ChessPositionImpl(chessPosition.getRow() + 1, chessPosition.getColumn() + 2);
    if(addingMoves(chessBoard, chessPosition, rightLeft) != null) {
      possibleMoves.add(addingMoves(chessBoard, chessPosition, rightLeft));
    }

    ChessPosition rightRight = new ChessPositionImpl(chessPosition.getRow() - 1, chessPosition.getColumn() + 2);
    if(addingMoves(chessBoard, chessPosition, rightRight) != null) {
      possibleMoves.add(addingMoves(chessBoard, chessPosition, rightRight));
    }

    return possibleMoves;
  }
  public ChessMove addingMoves(ChessBoard chessBoard, ChessPosition chessPosition, ChessPosition endPos) { //checks if the move can be added
    if(endPos.getColumn() < 8 && endPos.getColumn() >= 0 && endPos.getRow() < 8 && endPos.getRow() >= 0){ //checking if the end position is in bounds
      if(chessBoard.getPiece(endPos) == null){ //Knight can move if spot is empty
        ChessMove newMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE
        return newMove;
      }
      else if(chessBoard.getPiece(endPos).getTeamColor() != this.getTeamColor()){ //if the piece belongs to the other team, it is a valid move
        ChessMove newMove = new ChessMoveImpl(chessPosition, endPos, null); //TODO: FALSE
        return newMove;
      }
    }
    return null; //return null if the move doesn't meet conditions
  }
}
