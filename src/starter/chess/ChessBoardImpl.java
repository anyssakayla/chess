package chess;

import java.util.ArrayList;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard{

  //2d array instead
  //ArrayList<ArrayList<ChessPiece>> ChessOnBoard;
  ChessPiece [][] PiecesOnBoard; //2D array, but how does it use position?
  ChessPiece chessPiece;
  Map<ChessPosition, ChessPiece> ChessOnBoards; //later need to be able to serialize and deserialize and map i sharder
  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {//add a piece to the board based on it's position
    ChessOnBoards.put(position, piece);
  }

  @Override
  public ChessPiece getPiece(ChessPosition position) {
    ChessPiece associatedPiece = ChessOnBoards.get(position); //gets piece associated with key
    return associatedPiece;
  }

  @Override
  public void resetBoard() { //puts all pieces back to their normal spot
    ChessOnBoards.clear();
    chessPiece.getPieceType(); //WRONG get type R and put it at (1,1)
//    ChessOnBoards.put((1,1), ); //how do I add the first piece at 1,1.
    // Key only has one int available, can't use (1,1)
    //clear the map
    //create a new R at 1,1
  }
}
