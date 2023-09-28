package chess;

import java.util.ArrayList;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard{

  //2d array instead
  //ArrayList<ArrayList<ChessPiece>> ChessOnBoard;
  ChessPiece.PieceType [][] PiecesOnBoard = new ChessPiece.PieceType[8][8]; //2D array, but how does it use position?
  ChessPiece chessPiece;
  Map<ChessPosition, ChessPiece> ChessOnBoards; //later need to be able to serialize and deserialize and map i sharder
  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {//add a piece to the board based on it's position
    //TODO: does column or row need to go first?
    PiecesOnBoard[position.getColumn()][position.getRow()] = piece.getPieceType(); //sets the piecetype at its column and row
    ChessOnBoards.put(position, piece);
  }

  @Override
  public ChessPiece getPiece(ChessPosition position) {
    ChessPiece associatedPiece = ChessOnBoards.get(position); //gets piece associated with key
    return associatedPiece;
  }

  @Override
  public void resetBoard() { //puts all pieces back to their normal spot
    ChessOnBoards.clear(); //this is using the Map instead of 2D array
    chessPiece.getPieceType(); //WRONG get type R and put it at (1,1)
//    ChessOnBoards.put((1,1), ); //how do I add the first piece at 1,1.
    // Key only has one int available, can't use (1,1)
    //clear the map
    //create a new R at 1,1
    PiecesOnBoard = new ChessPiece.PieceType[8][8]; //set it to a new board

    PiecesOnBoard[1][1] = ChessPiece.PieceType.ROOK;
    PiecesOnBoard[1][2] = ChessPiece.PieceType.KNIGHT;
    PiecesOnBoard[1][3] = ChessPiece.PieceType.BISHOP;
    PiecesOnBoard[1][4] = ChessPiece.PieceType.QUEEN;
    PiecesOnBoard[1][5] = ChessPiece.PieceType.KING;
    PiecesOnBoard[1][6] = ChessPiece.PieceType.BISHOP;
    PiecesOnBoard[1][7] = ChessPiece.PieceType.KNIGHT;
    PiecesOnBoard[1][8] = ChessPiece.PieceType.ROOK;
    for(int i = 0; i < PiecesOnBoard[2].length; i++){ //put pawns on every column on row 2
      PiecesOnBoard[2][i] = ChessPiece.PieceType.PAWN;
    }
  }

}
