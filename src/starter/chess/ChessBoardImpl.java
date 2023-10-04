package chess;

import java.util.ArrayList;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard{

  //2d array instead
  //ArrayList<ArrayList<ChessPiece>> ChessOnBoard;
  ChessPiece[][] PiecesOnBoard = new ChessPiece[8][8]; //2D array, but how does it use position?

  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {//add a piece to the board based on it's position
    int curRow = position.getRow(); //get piece's row and column
    int curColumn = position.getColumn();
    PiecesOnBoard[curRow][curColumn] = piece; //set the piece at the specified row and column
  }

  //gets the piece at the indicated position
  @Override
  public ChessPiece getPiece(ChessPosition position) {
    int pieceRow = position.getRow();
    int pieceColumn = position.getColumn();

    ChessPiece associatedPiece = PiecesOnBoard[pieceRow][pieceColumn]; //gets piece associated with the position's row and column
    return associatedPiece;
  }

  @Override
  public void resetBoard() { //sets all pieces to their correct positions

    //TODO: Set team colors?
    ChessPositionImpl currPosition = new ChessPositionImpl(); //keeps track of each addition's position

    for(int i = 0; i < 8; i++){ //sets the bottom of the board
      currPosition.setRow(0);
      if(i == 0 || i == 7){ //every rook will be on the corner
        currPosition.setCollumn(i); //set the column to 0 and 7
        PiecesOnBoard[0][i].setPieceType(ChessPiece.PieceType.ROOK); //set the piecetype before adding
        addPiece(currPosition, PiecesOnBoard[0][i]); //add the piece at column i
      }
      if(i == 1 || i == 6){ //Knights are next to the rooks
        currPosition.setCollumn(i);
        PiecesOnBoard[0][i].setPieceType(ChessPiece.PieceType.KNIGHT);
        addPiece(currPosition, PiecesOnBoard[0][i]);
      }
      if(i == 2 || i == 5){ //set bishop in between knights
        currPosition.setCollumn(i);
        PiecesOnBoard[0][i].setPieceType(ChessPiece.PieceType.BISHOP);
        addPiece(currPosition, PiecesOnBoard[0][i]);
      }
      if(i == 3){
        currPosition.setCollumn(3);
        PiecesOnBoard[0][3].setPieceType(ChessPiece.PieceType.QUEEN);
        addPiece(currPosition, PiecesOnBoard[0][i]);
      }
      if(i == 4){
        currPosition.setCollumn(4);
        PiecesOnBoard[0][4].setPieceType(ChessPiece.PieceType.KING);
        addPiece(currPosition, PiecesOnBoard[0][i]);
      }
    }
    for(int i = 0; i < 8; i++){
      PiecesOnBoard[1][i].setPieceType(ChessPiece.PieceType.PAWN); //pawns on every spot on row 1
      currPosition.setCollumn(i); //set the column at i
      currPosition.setRow(1); //position will be on row 1
      addPiece(currPosition, PiecesOnBoard[1][i]); //add each piece to the board
    }

    //Sets the top of the board in the same pattern
    for(int i = 0; i < 8; i++){
      currPosition.setRow(7); //starting at the top of the board

      if(i == 0 || i == 7){ //every rook will be on the corner
        currPosition.setCollumn(i); //set the column to 0 and 7
        PiecesOnBoard[7][i].setPieceType(ChessPiece.PieceType.ROOK); //set the piecetype before adding
        addPiece(currPosition, PiecesOnBoard[7][i]); //add the piece at column i
      }
      if(i == 1 || i == 6){ //Knights are next to the rooks
        currPosition.setCollumn(i);
        PiecesOnBoard[7][i].setPieceType(ChessPiece.PieceType.KNIGHT);
        addPiece(currPosition, PiecesOnBoard[7][i]);
      }
      if(i == 2 || i == 5){ //set bishop in between knights
        currPosition.setCollumn(i);
        PiecesOnBoard[7][i].setPieceType(ChessPiece.PieceType.BISHOP);
        addPiece(currPosition, PiecesOnBoard[7][i]);
      }
      if(i == 3){
        currPosition.setCollumn(3);
        PiecesOnBoard[7][3].setPieceType(ChessPiece.PieceType.QUEEN);
        addPiece(currPosition, PiecesOnBoard[7][i]);
      }
      if(i == 4){
        currPosition.setCollumn(4);
        PiecesOnBoard[7][4].setPieceType(ChessPiece.PieceType.KING);
        addPiece(currPosition, PiecesOnBoard[7][i]);
      }
    }
    for(int i = 0; i < 8; i++){ //Set the pawns
      PiecesOnBoard[6][i].setPieceType(ChessPiece.PieceType.PAWN); //pawns on every spot on row 6
      currPosition.setCollumn(i); //set the column at i
      currPosition.setRow(6); //position will be on row 6
      addPiece(currPosition, PiecesOnBoard[6][i]); //add each piece to the board
    }

  }

}
