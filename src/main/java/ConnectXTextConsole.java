/**
 * This file generates the text console by which players interact
 * with a game of ConnectX.
 */
package ConnectX;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

/**
 * This class represents the console-based UI for a player who does not use the GUI.
 * @author Jordie Guzman
 * @version 25 February 2021
 */
public class ConnectXTextConsole {
    /** 
     * The ConnectX game grid. In the program each cell is either a space (for empty),
     * or 'X' or 'O', representing the players.
     */
    private char[][] grid = null; 
    
    /**
     * Initializes a ConnectX game console with a game grid.
     */
    public ConnectXTextConsole(char[][] grid) {
        this.grid = grid;
    }
    
    /**
     * Retrieves the current ConnectX game grid.
     * @return The current ConnectX game grid.
     */
    public char[][] getGrid() {
        return grid;
    }
    
    /**
     * Fills the ConnectX game grid with spaces.
     */
    private void intializeGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                grid[i][j] = ' ';
            }
        }
    }
    
    /**
     * Prints the current ConnectX game grid.
     */
    public void displayGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(j == grid[0].length-1) {
                    System.out.print("|" + grid[i][j] + "|");
                } else {
                    System.out.print("|" + grid[i][j]);
                }
            }
            System.out.println("\n");
        }
    }
    
    /**
     * Displays the "begin" state of a ConnectX game, with an grid of empty cells
     * and a message asking if the player would like to play against someone else
     * or the computer.
     */
    public void intializeGameDisplay() {
        intializeGrid();
        displayGrid();
        System.out.println("Begin Game. Enter 'P' if you want to play against another"
                + " player; enter 'C' to play against computer. ");
    }
    
    /**
     * Allows the user to type 'P' or 'C' into the console to let them
     * choose to play against a player or the computer, respectively.
     * 
     * This method handles the cases where a player inputs an inappropriate
     * char or inputs something other than a char.
     * 
     * @param game The ConnectX game being played.
     */
    public void initializeGameMode(ConnectX game) {
        Scanner chooseMode = new Scanner(System.in);
        try {
            char mode = chooseMode.next().charAt(0);
            if(!(mode =='P' || mode == 'C')) {
                System.out.println("Please type 'P' or 'C'.");
                initializeGameMode(game);
            } else {
                game.setMode(mode);
                if(mode == 'C') {
                    System.out.println("Start game against computer.");
                } else {
                    System.out.println("Start game against another player.");
                }
            }
        } catch(InputMismatchException e) {
            System.out.println("Please type 'P' or 'C'.");
            initializeGameMode(game);
        }
    }
    
    /**
     * Encapsulates the previous two methods into one.
     * @param game The ConnectX game being played.
     */
    public void intializeGame(ConnectX game) {
        intializeGameDisplay();
        initializeGameMode(game);
    }
     
    /**
     * Enables the user to play ConnectX against a person. Asks user for a choice 
     * of column (1-7) and, if their choice is valid, drops their piece in 
     * bottom-most empty cell of the column. It displays the current board and 
     * determines if a player has won or if there is a tie, ending the game either way.
     * 
     * This handles the exception that occurs if a player inputs something other 
     * than an int for a column choice.
     * 
     * @param game A ConnectX game.
     */
    public void playAgainstPerson(ConnectX game) {
        int col = 0;
        Scanner in1 = new Scanner(System.in);
        System.out.println("Player" + game.getCurrentPlayer() + " - your turn. "
                          + "Choose a" + " column number from 1 to " + grid[0].length + ". ");
        try {
            col = in1.nextInt() - 1;
            if(!game.isValidMove(grid, col)) {
                System.out.println("Not a valid move.");
                playAgainstPerson(game);
            } else {
                game.dropPiece(grid, col, game.getCurrentPlayer()); 
                displayGrid();
                if(game.isTie(grid)) {
                    System.out.println("Tie.");
                } else {
                    char player = game.getCurrentPlayer();
                    if(game.isWin(player, grid)) {
                        System.out.println("Player" + player + " won the game.");
                        System.exit(0);
                    } 
                }
                game.switchPlayer();
            }
        } catch (InputMismatchException e) {
            System.out.println("Please choose a number from 1 to " + grid[0].length + ". ");
            playAgainstPerson(game);
        }
    }     
    
    /**
     * Enables the user to play ConnectX against the computer. Asks user for a choice 
     * of column (1-7) and, if their choice is valid, drops their piece in 
     * bottom-most empty cell of the column. It displays the current board and 
     * determines if the player or computer has won or if there is a tie.
     * 
     * This handles the exception that occurs if a player inputs something other 
     * than an int for a column choice. After a player has gone, the computer makes its
     * turn. The player uses the 'X' piece and the computer uses the 'O' piece.
     * 
     * @param game The ConnectX game being played.
     * @param computer The computer opponent.
     */
    public void playAgainstComputer(ConnectX game, ConnectXComputerPlayer computer) {
        Scanner in = new Scanner(System.in);
        System.out.println("It is your turn. Choose a column from 1 to " + grid[0].length + ". ");
        int col = 0;
        try {
            col = in.nextInt() - 1;
            if(!game.isValidMove(grid, col)) {
                System.out.println("Not a valid move.");
                playAgainstComputer(game, computer);
            } else {
                game.dropPiece(grid, col, 'X');
                displayGrid();
                if(game.isTie(grid)) {
                    System.out.println("Tie.");
                } else {
                    if(game.isWin(game.getCurrentPlayer(), grid)) {
                    } else {
                        game.switchPlayer();
                        System.out.println("Computer turn.");
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Please choose a number from 1 to " + grid[0].length + ". ");
            playAgainstComputer(game, computer);
        }
    }

    /**
    * Lets the player choose the "X" part of ConnectX.
    */
    public int chooseX() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome! How many tokens in a row should a player get to win?");
        char choice = input.next().charAt(0);
        while(!Character.isDigit(choice)) {
            System.out.println("Please input a number.");
            choice = input.next().charAt(0);
        }
        return Integer.parseInt(""+choice);
    }

    public static void main(String[] args) {
        ConnectXTextConsole console = new ConnectXTextConsole(null);
        int x = console.chooseX();
        char[][] grid = new char[2*(x-1)][1+2*(x-1)];
        new ConnectX(x).playGame(new ConnectXTextConsole(grid), new ConnectXComputerPlayer());
    }
}



