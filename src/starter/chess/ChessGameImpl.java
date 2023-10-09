package chess;

import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame{
  public ChessBoard chessBoard = new ChessBoardImpl();
  public TeamColor teamColor;

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
    //TODO: CONFUSED, check tp and messenger
    //ChessPosition chessPosition = new ChessPositionImpl();
    ChessPiece currPiece = chessBoard.getPiece(startPosition); //set current piece to the starting position
    Collection<ChessMove> possMoves = new HashSet<ChessMove>(); //all possible moves
    Collection<ChessMove> legalMoves = new HashSet<ChessMove>(); //only moves that are legal

    return null;
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    ChessPosition moveStartPos = new ChessPositionImpl(move.getStartPosition().getRow(), move.getStartPosition().getColumn());
    ChessPosition movePosition = new ChessPositionImpl(move.getEndPosition().getRow(), move.getEndPosition().getColumn());
    if(chessBoard.getPiece(move.getEndPosition()) == null){ //if the end position is null, move there
      chessBoard.addPiece(movePosition, chessBoard.getPiece(moveStartPos)); //move picee
      chessBoard.erasePiece(moveStartPos); //erase old piece
    }
    else if(chessBoard.getPiece(movePosition).getTeamColor() != this.teamColor){ //if piece belongs to other team, move there
      chessBoard.addPiece(movePosition, chessBoard.getPiece(moveStartPos));
      chessBoard.erasePiece(moveStartPos);
    }
    else{ //if doesn't meet criteria, throw exception
      throw new InvalidMoveException();
    }
  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {

    return false;
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    return false;
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    return false;
  }

  @Override
  public void setBoard(ChessBoard board) {

  }

  @Override
  public ChessBoard getBoard() {
    return null;
  }
}
