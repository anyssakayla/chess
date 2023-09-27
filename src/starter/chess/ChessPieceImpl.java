package chess;

import java.util.Collection;

public class ChessPieceImpl implements ChessPiece{

  private PieceType pieceType;
  @Override
  public ChessGame.TeamColor getTeamColor() {
    return null;
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
