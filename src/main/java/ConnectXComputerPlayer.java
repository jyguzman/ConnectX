/*
 * This file contains the code that controls a computer player during a game
 * ConnectX.
 */
package ConnectX;
import java.util.Random;

/**
 * This class represents the computer during a game of ConnectX, if
 * that a player has chosen to play against the computer.
 * @author Jordie Guzman
 * @version 25 February 2021
 */
public class ConnectXComputerPlayer {
    
    /**
     * Initializes a computer player; there are currently no attributes 
     * to initialize.
     */
    public ConnectXComputerPlayer() {
    }
    
    /**
     * This method allows the computer to choose randomly any valid spot
     * on the board, meaning any column that has not been filled. The computer
     * uses the 'O' piece.
     * @param game - The ConnectX game being played.
     * @param console - The ConnectX UI currently in use.
     */
    public void computerTurn(ConnectX game, ConnectXTextConsole console){
        Random rand = new Random();
        int r = rand.nextInt(1+2*(game.getNumInRow()-1));
        if(game.isValidMove(console.getGrid(), r)) {
            //Drop piece in random column.
            game.dropPiece(console.getGrid(), r, 'O'); 
            console.displayGrid();
            if(game.isTie(console.getGrid())) {
                System.out.println("Tie.");
            } else {
                char player = game.getCurrentPlayer();
                if(game.isWin(player,console.getGrid())) {
                    if(player == 'X') {
                        System.out.println("You won the game.");
                    } else {
                        System.out.println("Computer won the game.");
                    }
                    System.exit(0);
                } else {
                    game.switchPlayer();
                }
            }
        } else {
            //If filled column was slected, try again.
            computerTurn(game, console);
        }
    }
}


