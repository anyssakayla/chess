package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 */
public class UserGameCommand {


    public UserGameCommand(int gameID, String authToken, ChessGame.TeamColor teamColor, CommandType type, ChessMove move){
        this.gameID = gameID;
        this.authToken = authToken;
        this.teamColor = teamColor;
        this.chessMove = move;
        this.commandType = type;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;
    private final int gameID;
    private final String authToken;
    private final ChessGame.TeamColor teamColor;
    private final ChessMove chessMove;

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() { return this.commandType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGameCommand)) return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
