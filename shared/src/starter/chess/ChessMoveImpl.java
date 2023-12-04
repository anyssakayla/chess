package chess;

import java.util.Objects;

public class ChessMoveImpl implements ChessMove{
  ChessPosition startingPos;
  ChessPosition endingPos;
  ChessPiece chessPiece;
  ChessPiece.PieceType promotionPiece;
  boolean promotion = false;

  public  ChessMoveImpl(ChessPosition startingPos, ChessPosition endingPos, boolean promotion ){
    this.startingPos = startingPos;
    this.endingPos = endingPos;
    promotionPiece = null;
    this.promotion = promotion;
  }
  public ChessMoveImpl() { //create an empty ChessMove instance
    startingPos = null;
    endingPos = null;
    promotionPiece = null;
  }

  public  ChessMoveImpl(ChessPosition startingPos, ChessPosition endingPos, ChessPiece.PieceType pieceType){
    this.startingPos = startingPos;
    this.endingPos = endingPos;
    promotionPiece = pieceType; //sets the promotion piece to the one given
  }
  @Override
  public ChessPosition getStartPosition() { //where move starts from
    return startingPos;
  }

  @Override
  public ChessPosition getEndPosition() {
    return endingPos;
  }

  @Override
  public ChessPiece.PieceType getPromotionPiece() {
    return promotionPiece;
  }
  @Override
  public void setPromotion(ChessPiece.PieceType pieceType){
    promotionPiece = pieceType;
  }

  @Override
  public void setStartingPos(ChessPosition startingPos){
    this.startingPos = startingPos;
  }

  @Override
  public void setEndingPos(ChessPosition endingPos){
    this.endingPos = endingPos;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessMoveImpl chessMove=(ChessMoveImpl) o;
    return promotion == chessMove.promotion && Objects.equals(startingPos, chessMove.startingPos) && Objects.equals(endingPos, chessMove.endingPos) && Objects.equals(chessPiece, chessMove.chessPiece) && promotionPiece == chessMove.promotionPiece;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startingPos, endingPos, chessPiece, promotionPiece, promotion);
  }
}
