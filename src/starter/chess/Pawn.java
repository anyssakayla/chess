package chess;
import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPieceImpl{
  public Pawn(ChessGame.TeamColor teamColor){
    super(teamColor, PieceType.PAWN);
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition chessPosition){

    // check for color of pawn/team color
    //have position
    //check what direction it can go
    //check if it is in a promotion location
    //can pawn "kill" other pieces


    return null;
  }
}
