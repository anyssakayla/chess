package chess;

public class ChessMoveImpl implements ChessMove{
  ChessPosition startingPos;
  ChessPosition endingPos;
  ChessPiece chessPiece;
  ChessPiece.PieceType promotionPiece;
  boolean promotion = false;

  public  ChessMoveImpl(ChessPosition startingPos, ChessPosition endingPos, boolean promotion ){
    this.startingPos = startingPos;
    this.endingPos = endingPos;
    this.promotion = promotion;
  }
  @Override
  public ChessPosition getStartPosition() { //where move starts from
    return null;
  }

  @Override
  public ChessPosition getEndPosition() {
    return null;
  }

  @Override
  public ChessPiece.PieceType getPromotionPiece() {
    return promotionPiece;
  }

  public void setPromotion(ChessPiece.PieceType pieceType){
    promotionPiece = pieceType;
  }
}
