package chess;

/**
 * A chessboard that can hold and rearrange chess pieces
 */
public interface ChessBoard {

    /**
     * Adds a chess piece to the chessboard
     * @param position where to add the piece to
     * @param piece the piece to add
     */
    void addPiece(ChessPosition position, ChessPiece piece);

    /**
     * Gets a chess piece on the chessboard
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that position
     */
    ChessPiece getPiece(ChessPosition position);

    /**
     * erases a chess piece on the chessboard based on position
     * @param chessPosition The position to erase the piece from
     */
    void erasePiece(ChessPosition chessPosition);

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    void resetBoard();

    /**
     *
     * @param chessMove gives the move to be completed
     */
    void completesMove(ChessMove chessMove);
}
