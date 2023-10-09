package chess;

import java.util.Collection;

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
    return null;
  }
}
