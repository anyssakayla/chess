package ui;
import java.util.Locale;
import java.util.Scanner;
import ui.EscapeSequences.*;


public class ClientMain {
  public static void main(String[] args){
    String textColorReset = EscapeSequences.RESET_TEXT_COLOR;
    try{
      Client client = new Client();
      System.out.println("Welcome to Anyssa's chess game. Type help to get started.");
      Scanner scanner = new Scanner(System.in);

       var userInput = "";
       while(!userInput.equals("quit")){ //!userInput.toLowerCase(Locale.ROOT).equals(equals("quit"))

         client.currentState();
         String usrInput = scanner.nextLine(); //just set to userInput

         try{
           userInput = client.checkInput(usrInput); //put evaluation here
           System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + userInput);
         }catch(Exception exception){
           System.out.println(exception.getMessage());
         }
       }

    }catch (Exception exception){
      //String setWhite = EscapeSequences.SET_BG_COLOR_WHITE;
      
      System.out.println("Error occurred while connecting to the server");
    }
  }
}
