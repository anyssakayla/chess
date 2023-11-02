package chess;
import java.util.ArrayList;
import java.util.Map;

public class ChessBoardImpl implements ChessBoard{

  ChessPiece[][] PiecesOnBoard = new ChessPiece[8][8]; //2D array of board

//  public static Gson serializer(){
//    var obj = new Gson().fromJson("{'squares':)
//  }



  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {//add a piece to the board based on it's position
    int curRow = position.getRow(); //get piece's row and column
    int curColumn = position.getColumn();
    PiecesOnBoard[curRow][curColumn] = piece; //set the piece at the specified row and column
  }

  public void erasePiece(ChessPosition chessPosition){ //erase pieces on the board
    int curRow = chessPosition.getRow();
    int curColumn = chessPosition.getColumn();
    PiecesOnBoard[curRow][curColumn] = null;
  }

  @Override
  public void completesMove(ChessMove chessMove){ //completes the move
    ChessPiece pieceToMove = getPiece(chessMove.getStartPosition()); //piece to move is at the start position
    addPiece(chessMove.getEndPosition(), pieceToMove); //put the piece to move at the end position
    //erasePiece(chessMove.getStartPosition()); //does this one work?
    addPiece(chessMove.getStartPosition(), null); //set the start piece to null for an empty space on board
    if(chessMove.getPromotionPiece() != null){
      pieceToMove.setPieceType(chessMove.getPromotionPiece());
    }
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
    ChessGame.TeamColor teamColor = null;
    for(int i = 0; i < 8; i++){ //row
      for(int j = 0; j < 8; j++){ //column
        ChessPositionImpl currPosition = new ChessPositionImpl(i,j);
        if(i == 0 || i == 1){ //if in the first two rows, assign to white team
          teamColor = ChessGame.TeamColor.WHITE; //if bottom of board, set to white
          ChessPiece chessPiece = new ChessPieceImpl(teamColor); //set teamcolor to white
          if(i == 1){ //if row 1, all are pawns
            chessPiece.setPieceType(ChessPiece.PieceType.PAWN); //set to white pawns
            addPiece(currPosition, chessPiece); //adds the piece based on position and piecetype
          }
          if(i == 0){ //make sure it is the first row
            if(j == 0 || j == 7){ //rooks are on 0 and 7
              chessPiece.setPieceType(ChessPiece.PieceType.ROOK); //set piece to rook
              addPiece(currPosition, chessPiece); //add the piece
            }
            if(j == 1 || j == 6){
              chessPiece.setPieceType(ChessPiece.PieceType.KNIGHT);
              addPiece(currPosition, chessPiece);
            }
            if(j == 2 || j == 5){
              chessPiece.setPieceType(ChessPiece.PieceType.BISHOP);
              addPiece(currPosition, chessPiece);
            }
            if(j == 4){
              chessPiece.setPieceType(ChessPiece.PieceType.KING);
              addPiece(currPosition, chessPiece);
            }
            if(j == 3){
              chessPiece.setPieceType(ChessPiece.PieceType.QUEEN);
              addPiece(currPosition, chessPiece);
            }
          }
        }
        if(i == 6 || i == 7){ //if row 6 or 7, team is black
          teamColor = ChessGame.TeamColor.BLACK; //if top of board, set to black
          ChessPiece chessPiece = new ChessPieceImpl(teamColor); //set teamcolor to black

          if(i == 6){ //if row 6, all are pawns
            chessPiece.setPieceType(ChessPiece.PieceType.PAWN); //set to black pawns
            addPiece(currPosition, chessPiece); //adds the piece based on position and piecetype
          }
          if(i == 7){ //make sure pieces are placed on top row
            if(j == 0 || j == 7){ //rooks are on 0 and 7
              chessPiece.setPieceType(ChessPiece.PieceType.ROOK); //set piece to rook
              addPiece(currPosition, chessPiece); //add the piece
            }
            if(j == 1 || j == 6){
              chessPiece.setPieceType(ChessPiece.PieceType.KNIGHT);
              addPiece(currPosition, chessPiece);
            }
            if(j == 2 || j == 5){
              chessPiece.setPieceType(ChessPiece.PieceType.BISHOP);
              addPiece(currPosition, chessPiece);
            }
            if(j == 4){
              chessPiece.setPieceType(ChessPiece.PieceType.KING);
              addPiece(currPosition, chessPiece);
            }
            if(j == 3){
              chessPiece.setPieceType(ChessPiece.PieceType.QUEEN);
              addPiece(currPosition, chessPiece);
            }
          }
        }
      }
    }
  }


}
