package ui;

import Model.Game;
import chess.*;

import java.util.Arrays;
import java.util.Locale;
import static ui.EscapeSequences.*;
public class Client {
  String textColor = EscapeSequences.RESET_TEXT_COLOR;
  String blueText = EscapeSequences.SET_TEXT_COLOR_BLUE;
  String greenText = SET_TEXT_COLOR_GREEN;
  String whiteKing = WHITE_KING;
  String whiteQueen = WHITE_QUEEN;
  String whiteBishop = WHITE_BISHOP;
  String whiteKnight = WHITE_KNIGHT;
  String whiteRook = WHITE_ROOK;
  String blackKing = BLACK_KING;
  String blackQueen = BLACK_QUEEN;
  String blackBishop = BLACK_BISHOP;
  String blackKnight = BLACK_KNIGHT;
  String blackRook = BLACK_ROOK;
  final private ServerFacade serverFacade;

  private String userAuthToken = null;
  enum State {LOGGED_IN, LOGGED_OUT, OBSERVING, WHITE_PLAYER, BLACK_PLAYER};

  private State currState = State.LOGGED_OUT;
  public Client(){
    serverFacade = new ServerFacade("http://localhost:8080");
  }

  public void currentState(){
    System.out.print(greenText + String.format("[%s] >>> ", currState) + blueText);
  }

  public String checkInput(String userInput){
    String input = "Error with command. Try using \"help\"";
    userInput = userInput.toLowerCase(Locale.ROOT);
    String[] args = userInput.split(" ");
    String[] arguments = Arrays.copyOfRange(args, 1, args.length); //could just take parameters as args[1] or args[2] in functions

    if(args[0].toLowerCase(Locale.ROOT).equals("help")){
       help();
       return "";
    }
    if(args[0].toLowerCase(Locale.ROOT).equals("quit")){
      currState = State.LOGGED_OUT;
      return "Thanks for playing";
    }
    try{
      if(args[0].toLowerCase(Locale.ROOT).equals("register")){
        if(arguments.length < 3){
          return "You must include a username, password, and email to register";
        }
        return handleRegister(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("login")){
        if(arguments.length < 2){
          return "You must include a username and password to login";
        }
        return handleLogin(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("logout")){
        return handleLogout(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("create")){
        return handleCreateGame(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("join")){
        return handleJoin(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("observe")){
        return handleJoin(arguments);
      }
      else if(args[0].toLowerCase(Locale.ROOT).equals("list")){
        return handleList();
      }
      else{
        return "";
      }
    }catch (Exception e){

    }
    return "";
  }


  public void help(){
    if(currState == State.LOGGED_IN){
      loggedInHelp();
    }
    if(currState == State.LOGGED_OUT){
      loggedOutHelp();
    }
  }

  public void loggedOutHelp(){
    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
    System.out.println("login <USERNAME> <PASSWORD> - to play chess");
    System.out.println("quit - playing chess");
    System.out.println("help - with possible commands");
  }
  public void loggedInHelp(){
    System.out.println("create <NAME> - a game");
    System.out.println("list - games");
    System.out.println("join <ID> [WHITE|BLACK|<empty>] - a game");
    System.out.println("observe <ID> - a game");
    System.out.println("logout - when you are done");
    System.out.println("quit - playing chess");
    System.out.println("help - with possible commands");
  }


  public String handleRegister(String[] arguments){
    if(currState == State.LOGGED_OUT){
      if(arguments.length == 3){
        var result = serverFacade.register(arguments[0], arguments[1], arguments[2]);
        userAuthToken = result; //serverFacade register returns the authToken
        if(result != null){
          currState = State.LOGGED_IN;
         // loggedInHelp();
        }else{
          return "Error occurred";
        }
      }
    }
    return ""; //set to nothing if it works
  }

  public String handleLogin(String[] arguments){
    if(currState == State.LOGGED_OUT){
      if(arguments.length == 2){
        userAuthToken = serverFacade.login(arguments[0], arguments[1]);
        currState = State.LOGGED_IN;
       // loggedInHelp(); //should not display unless they type help
        return "";
      }
      return "Login attempt invalidl";
    }
    return "Failed to login";
  }

  public String handleLogout(String[] arguments){
    if(userAuthToken == null || currState == State.LOGGED_OUT){
      return "You must be logged in before logging out";
    }
    serverFacade.logout(userAuthToken); //pass in the authToken to be logged out
    currState = State.LOGGED_OUT;
    userAuthToken = null;
    return "";
  }

  public String handleCreateGame(String[] arguments){
    if(userAuthToken == null ){
      return "You must be logged in before creating a game";
    }
    if(currState == State.LOGGED_IN){
      var game = serverFacade.createGame(userAuthToken, arguments[0]); //should this be args[1]?
      return String.format("Game %d created", game); //we already have a print statement
    }
    return "Failed to create a new game";
  }

  public String handleList(){
    if(userAuthToken == null ){
      return "You must be logged in before creating a game";
    }
    serverFacade.list(userAuthToken); //this also prints out the list
    return "";
  }

  public String handleJoin(String[] arguments){
    if(userAuthToken == null ){
      return "You must be logged in before creating a game";
    }
    if(currState == State.LOGGED_IN){
      if(arguments.length == 1){
        String noTeamColor = null;
        var gameID = arguments[0];
        var joined = serverFacade.joinGame(gameID, null, userAuthToken);
        if(joined){
          joinedGame();
        }
      }
      if(arguments.length == 2){
        var teamColor = arguments[1].toUpperCase(Locale.ROOT);
        var gameID = arguments[0];
        var joined = serverFacade.joinGame(gameID, teamColor, userAuthToken);
        if(joined){
          joinedGame();
        }
      }
    }
    return "";
  }

  public void joinedGame(){
    var game = new Game();
    game.setGame(new ChessGameImpl());
    var thisGame = game.getGame();
    var board = (ChessBoardImpl) thisGame.getBoard();
    board.resetBoard();
    String topLetters = "    a  b  c  d  e  f  g  h    ";
    String topLettersReversed = "    h  g  f  e  d  c  b  a    ";
    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    System.out.print(SET_TEXT_COLOR_BLACK);
    System.out.print(topLettersReversed);
    System.out.print(SET_BG_COLOR_BLACK);
    System.out.print("\n");

    for(int row = 0; row < 8; row++){
      int spotOnBoard = row + 1;
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.printf(" %d ", spotOnBoard);
      for(int column = 0; column < 8; column++){
        var chessPosition = new ChessPositionImpl(row, column);
        if(((row + column) % 2) == 0){ //if it is an even block, make it white
          System.out.print(SET_BG_COLOR_WHITE);
        }else{
          System.out.print(SET_BG_COLOR_BLACK);
        }
        displayPieces(board, chessPosition);
      }
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.print(SET_TEXT_COLOR_BLACK);
      System.out.printf(" %d ", spotOnBoard);
      System.out.print(SET_BG_COLOR_BLACK);
      System.out.print("\n");
    }
    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    System.out.print(SET_TEXT_COLOR_BLACK);
    System.out.print(topLettersReversed);
    System.out.print(SET_BG_COLOR_BLACK);
    System.out.print("\n\n");

    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    System.out.print(SET_TEXT_COLOR_BLACK);
    System.out.print(topLetters);
    System.out.print(SET_BG_COLOR_BLACK);
    System.out.print("\n");

    for(int row = 7; row >= 0; row--){
      int spotOnBoard = row + 1;
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.printf(" %d ", spotOnBoard);
      for(int column = 7; column >= 0; column--){
        var chessPosition = new ChessPositionImpl(row, column);
        if(((row + column) % 2) == 0){ //if it is an even block, make it white
          System.out.print(SET_BG_COLOR_WHITE);
        }else{
          System.out.print(SET_BG_COLOR_BLACK);
        }
        displayPieces(board, chessPosition);
      }
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.print(SET_TEXT_COLOR_BLACK);
      System.out.printf(" %d ", spotOnBoard);
      System.out.print(SET_BG_COLOR_BLACK);
      System.out.print("\n");
    }
    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    System.out.print(SET_TEXT_COLOR_BLACK);
    System.out.print(topLetters);
    System.out.println("\u001b" + "[48;49;" + "15m");
    System.out.print(SET_TEXT_COLOR_WHITE);
    System.out.print(SET_BG_COLOR_BLACK + "\n");

  }

  public void displayPieces(ChessBoardImpl board, ChessPositionImpl position){
    String whitePawn = WHITE_PAWN;
    String blackPawn = BLACK_PAWN;
    var piece = board.getPiece(position);
    if(piece == null){
      System.out.print(SET_TEXT_COLOR_BLACK);
    }else{
      if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(SET_TEXT_COLOR_RED);
      }else{
        System.out.print(SET_TEXT_COLOR_BLUE);
      }
    }
    if(piece == null){ //there should be no piece here, so print blank
      System.out.print("   ");
    }else{
      if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print( whitePawn);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackPawn);
      }else if(piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(whiteRook);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackRook);
      }else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(whiteBishop);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackBishop);
      }else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(whiteKnight);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackKnight);
      }else if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(whiteKing);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackKing);
      }else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.WHITE){
        System.out.print(whiteQueen);
      }
      else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.BLACK){
        System.out.print(blackQueen);
      }
    }
  }
  //      Client client = new Client();
  //      String backgnd = EscapeSequences.SET_BG_COLOR_WHITE;
  //      String reset = EscapeSequences.RESET_TEXT_COLOR;
  //      String BLUE = EscapeSequences.SET_TEXT_COLOR_BLUE;
  //
  //      System.out.println( backgnd + reset  + "Welcome to chess, type help to get started");
  //      Scanner scanner = new Scanner(System.in);
  //
  //      var userInput = "";
  //      while(true){
  //
  //
  //        userInput = scanner.nextLine();
  //        args = userInput.split(" ");
  //        if(args.length == 0){
  //          System.out.println("invalid input, please try again");
  //          continue;
  //        }
  //        if(args[0].equals("quit")){
  //          return;
  //        }
  //        else if(args[0].equals("help")){
  //          System.out.println( BLUE + "register <USERNAME> <PASSWORD> <EMAIL> " + reset +"- to create an account");
  //          System.out.println(BLUE + "login <USERNAME> <PASSWORD> - to play chess");
  //          System.out.println(BLUE + "quit - playing chess");
  //          System.out.println(BLUE + "help - with possible commands");
  //        }
  //        else if(args[0].equals("login") && args.length == 3){
  //
  //          String username = args[1];
  //          String password = args[2];
  //          System.out.println(username);
  //
  //        }
  //        else if(args[0].equals("register") && args.length == 4){
  //          String username = args[1];
  //          String password = args[2];
  //          String email = args[3];
  //
  //        }
  //        else{
  //          System.out.println("invalid input, please try again");
  //          continue;
  //        }
  //      }
}
