package chess;

import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame{
  public ChessBoard chessBoard = new ChessBoardImpl();
  public TeamColor teamColor;

  @Override
  public TeamColor getTeamTurn() {
    return teamColor;
  }

  @Override
  public void setTeamTurn(TeamColor team) {
    teamColor = team;
  }

  @Override
  public Collection<ChessMove> validMoves(ChessPosition startPosition) {

    //ChessPosition chessPosition = new ChessPositionImpl();
    ChessPiece currPiece = chessBoard.getPiece(startPosition); //set current piece to the starting position
    Collection<ChessMove> possMoves = new HashSet<ChessMove>(); //all possible moves
    Collection<ChessMove> legalMoves = new HashSet<ChessMove>(); //only moves that are legal

    ChessMove tempMove = new ChessMoveImpl(); //temp move will be used to make a move and check if it is valid for inCheck
    ChessPiece capture;
    for(ChessMove chessMove : possMoves){
      tempMove.setStartingPos(chessMove.getEndPosition());
      tempMove.setEndingPos(chessMove.getStartPosition());
      tempMove.setPromotion(currPiece.getPieceType());

      capture = chessBoard.getPiece(chessMove.getEndPosition()); //pass in the piece that is captured
      chessBoard.completesMove(chessMove);
      if(!isInCheck(currPiece.getTeamColor())){ //if it is not in check, add the move to legal moves
        legalMoves.add(chessMove);
      }
      chessBoard.completesMove(tempMove);
      chessBoard.addPiece(chessMove.getEndPosition(), capture);
    }
    return legalMoves;
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    if(this.teamColor != null){
      if(chessBoard.getPiece(move.getStartPosition()).getTeamColor() != this.teamColor){
        throw new InvalidMoveException("This piece does not belong to the playing team");
      }
    }
    Collection<ChessMove> legalMoves = validMoves(move.getStartPosition()); //put all valid moves into a collection of legal moves
    if(!legalMoves.contains(move)){ //if the move is not legal, throw exception
      throw new InvalidMoveException("This move is not valid for the indicated piece");
    }
    chessBoard.completesMove(move);
    if(teamColor == TeamColor.WHITE){
      setTeamTurn(TeamColor.BLACK);
    }
    else{
      setTeamTurn(TeamColor.BLACK);
    }

  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    ChessPosition kingPos = new ChessPositionImpl(0, 0);

    for(int i = 0; i < 8; i ++){
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getPieceType() == ChessPiece.PieceType.KING){ //if the piece at the position is a king
            if(chessBoard.getPiece(position).getTeamColor() == teamColor){ //if it is equal to this team's color, it is the king we are looking for
              kingPos.setRow(i);
              kingPos.setCollumn(j);
            }
          }
        }
      }
    }
    for(int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        position.setRow(i); //set the row anc column to i and j
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null){ //if there is a piece at position
          if(chessBoard.getPiece(position).getTeamColor() != teamColor){ //if piece is from opposite team
            teamMoves = chessBoard.getPiece(position).pieceMoves(chessBoard, position); //call the piece's pieceMoves
            for(ChessMove move : teamMoves){
              if(move.getEndPosition().equals(kingPos)){ //if they can have a move that equals the king's position
                return true;
              }
            }
          }
        }
      }
    }
    return false; //if it does not meet criteria for being in check, return false
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    if(!isInCheck(teamColor)){
      return false;
    }
    for(int i = 0 ; i < 8; i++){
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);

        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getTeamColor() == teamColor){
            teamMoves = validMoves(position);
            if(!teamMoves.isEmpty()){
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    Collection<ChessMove> teamMoves;
    ChessPosition position = new ChessPositionImpl(0, 0);
    if(isInCheck(teamColor)){
      return false;
    }

    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        position.setRow(i);
        position.setCollumn(j);
        if(chessBoard.getPiece(position) != null){
          if(chessBoard.getPiece(position).getTeamColor() == teamColor){
            teamMoves = validMoves(position);
            if(!teamMoves.isEmpty()){ //if it contains moves, it is not in stalemate
              return false;
            }
          }
        }
      }
    }
    return true; //if it doesn't meet conditions, then they are in stalemate
  }

  @Override
  public void setBoard(ChessBoard board) {
    if(board.getClass() == chessBoard.getClass()){
      chessBoard = board;
    }
  }

  @Override
  public ChessBoard getBoard() {
    return chessBoard;
  }
}
