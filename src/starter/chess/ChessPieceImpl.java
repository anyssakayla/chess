package chess;

import java.util.Collection;
import java.util.HashSet;

public class ChessPieceImpl implements ChessPiece{
  public ChessGame.TeamColor teamColor = null;
  private PieceType pieceType;

  public ChessPieceImpl(ChessGame.TeamColor color, PieceType pieceType){
    teamColor = color;
    this.pieceType = pieceType;
  }
  public ChessPieceImpl(ChessGame.TeamColor color){
    teamColor = color;

  }
  @Override
  public ChessGame.TeamColor getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(ChessGame.TeamColor teamColor){
    this.teamColor = teamColor;
  }

  public void setPieceType(PieceType pieceType) {
    this.pieceType = pieceType;
  }

  @Override
  public PieceType getPieceType() {
    return pieceType;
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> currentPieceMoves;
    if(pieceType == PieceType.PAWN){
      Pawn piece = new Pawn(teamColor);
      currentPieceMoves = piece.returnMoves(board, myPosition);
      return currentPieceMoves;
    }
    if(pieceType == PieceType.BISHOP){
      Bishop piece = new Bishop(teamColor);
      currentPieceMoves = piece.returnMoves(board, myPosition);
      return currentPieceMoves;
    }
    if(pieceType == PieceType.KING){
      King piece = new King(teamColor);
      currentPieceMoves = piece.returnMoves(board, myPosition);
      return currentPieceMoves;
    }
    if(pieceType == PieceType.KNIGHT){
      Knight piece = new Knight(teamColor);
      currentPieceMoves = piece.returnMoves(board, myPosition);
      return currentPieceMoves;
    }
    if(pieceType == PieceType.QUEEN){
      Queen piece = new Queen(teamColor);
      currentPieceMoves = piece.returnMoves(board, myPosition);
      return currentPieceMoves;
    }
    if(pieceType == PieceType.ROOK){
      Rook piece = new Rook(teamColor);
      currentPieceMoves = piece.returnMoves(board,myPosition);
      return currentPieceMoves;
    }
    return null;
  }
}
