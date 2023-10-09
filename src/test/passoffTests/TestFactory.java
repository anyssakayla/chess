package passoffTests;

import chess.*;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
		return new ChessBoardImpl();
    }

    public static ChessGame getNewGame(){
		return new ChessGameImpl();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
      if(type == ChessPiece.PieceType.BISHOP){
        return new Bishop(pieceColor);
      }
      if(type == ChessPiece.PieceType.KING){
        return new King(pieceColor);
      }
      if(type == ChessPiece.PieceType.KNIGHT){
        return new Knight(pieceColor);
      }
      if(type == ChessPiece.PieceType.PAWN){
        return new Pawn(pieceColor);
      }
      if(type == ChessPiece.PieceType.QUEEN){
        return new Queen(pieceColor);
      }
      else{
        return new Rook(pieceColor);
      }
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
		return new ChessPositionImpl(row - 1, col - 1);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){

      if(promotionPiece != null){
        return new ChessMoveImpl(startPosition, endPosition, promotionPiece);
        }
      else{
        return new ChessMoveImpl(startPosition, endPosition, false);
      }
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}
