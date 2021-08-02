/**
 * This file contains the core game logic, such as the rules, for a ConnectX game and
 * launches the game.
 */
package ConnectX;
import java.util.*;

/**
 * This class represents the core logic of a game of ConnectX, including the game's
 * rules. 
 * @author Jordie Guzman
 * @version 25 February 2021
 */
public class ConnectX {
    /** This keeps track of whose turn it is: X or O. */
    private char currentPlayer;

    /** This field if we are in a game against another player or the computer. */
    private char gameMode;

    /** This field denotes how many Xs or Os in a row are needed to win a game. */
    private int numInRow;
        
    /**
     * Initializes a ConnectX game with player X getting the first turn and
     * game mode to be set by the player.
     */
    public ConnectX(int numInRow) {
        currentPlayer = 'X';
        gameMode = ' ';
        this.numInRow = numInRow;
    }
    
    /**
     * Retrieves whose turn (X or O) it currently is.
     * @return The current player as a char: 'X' or 'O'.
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Sets the current player.
     * @param The current player as a char: 'X' or 'O'.
     */
    public void setCurrentPlayer(char player) {
        currentPlayer = player;
    }
    
    /**
     * Returns 'C' if player is playing against the computer, and 'P' if playing agains
     * another player.
     * @return char 'C' if currently playing against computer, 'P' otherwise.
     */
    public char getMode() {
        return gameMode;
    }

    public int getNumInRow() {
        return numInRow;
    }
    
    /**
     * Sets the mode of the game to 'P' if the player chose to play against
     * another player, or to 'C' if they chose to play against the computer.
     * @param mode - 'P' to play against player, 'C' to play against computer
     */
    public void setMode(char mode) {
        gameMode = mode;
    }
    
    /**
     * This method locates and returns the bottom-most empty row
     * given a column index (i.e., if column 7 is selected and
     * and there already three pieces at the bottom, three will
     * be returned because row three is the first empty row).
     * @param grid The ConnectX game grid.
     * @param col The index of the chosen column.
     * @return The index of the row with the bottom-most empty cell.
     */
    public int bottomMostRow(char[][] grid, int col) {
        int bottomMostRow = -1;
        if(grid[grid.length - 1][col] == ' ') {
             return grid.length - 1;
        } else {
            for(int i = 0; i < grid.length - 1; i++) {
                if(grid[i][col] == ' ') {
                    bottomMostRow = i;
                }
           }  
        }
        return bottomMostRow;
    }
    
    /**
     * Drops a piece into the first empty row of a selected column.
     * @param grid The ConnectX game grid.
     * @param col The index of the chosen column.
     * @param c The piece to be dropped: 'X' or 'O'.
     */
    public void dropPiece(char[][] grid, int col, char c) {
        grid[bottomMostRow(grid, col)][col] = c;
    }
    
    /**
     * Determines whether or not a player can make a move. False if
     * the player inputs 0 or anything above the number of columns, or if a column
     * is already full. Returns true otherwise.
     * @param grid The ConnectX game grid.
     * @param col The index of the selected column.
     * @return True if the player selects an appropriate column and the column is not full.
     */
    public boolean isValidMove(char[][] grid, int col) {
        return col >= 0 && col <= grid[0].length - 1 
               && bottomMostRow(grid, col) != -1;       
    }

    /**
     * Sets the current player to 'X' or 'O' depending on whose turn 
     * it just was.
     */
    public void switchPlayer() {
        if(currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }
    
    /**
     * Returns true if are numInRow consecutive Xs or four consecutive Os in any
     * of the rows of the game grid.
     * @param grid The ConnectX game grid.
     * @param c X or O
     * @return True if there are four consecutive Xs or Os in any of the rows.
     */
    public boolean isConsecutiveRow(char c, char[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < numInRow; j++) {
                int count = 0;
                for(int k = 0; k < numInRow; k++) {
                    if(grid[i][j+k] == c)
                        count++;
                }
                if(count == numInRow) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are numInRow consecutive Xs or four consecutive Os in
     * any column of the game grid.
     * @param grid The ConnectX game grid.
     * @param c X or O
     * @return True if there four consecutive Xs or Os in any column.
     */
    public boolean isConsecutiveColumn(char c, char[][] grid) {
        for(int i = 0; i < grid[0].length - numInRow; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                int count = 0;
                for(int k = 0; k < numInRow; k++) {
                    if(grid[i+k][j] == c)
                        count++;
                }
                if(count == numInRow) return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if there are numInRow consecutive Xs or four consecutive Os in 
     * a diagonal on the game grid.
     * @param grid The ConnectX game grid.
     * @param c X or O
     * @return Returns true if there are four consecutive diagonal Xs or Os.
     */
    public boolean isConsecutiveDiagonal(char c, char[][] grid) {
        for(int i = 0; i < numInRow - 1; i++) {
            for(int j = 0; j < numInRow; j++) {
                int count = 0;
                for(int k = 0; k < numInRow; k++) {
                    int length = grid[0].length;
                    if(grid[i+k][j+k] == c)
                        count++;
                    else {
                        if(grid[i+k][length-j-(k+1)] == c)
                            count++;
                    }
                }
                if(count == numInRow) return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if a player has won, i.e. if there numInRow consecutive Xs or Os are
     * found on the game grid.
     * @param grid The ConnectX game grid.
     * @param c X or O (the players)
     * @return True if a player has won.
     */
    public boolean isWin(char c, char[][] grid) {
        return isConsecutiveDiagonal(c, grid) || isConsecutiveRow(c, grid) 
               || isConsecutiveColumn(c, grid);
    }
    
    /**
     * Returns true if there is a tie, i.e. if there no consecutive Xs or Os on the
     * game grid and the grid is filled.
     * @param grid The ConnectX game grid.
     * @return True if there is a tie.
     */
    public boolean isTie(char[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * CLears the game grid.
     * @param grid The ConnectX game grid.
     * @return The cleared grid.
     */
    public char[][] clearGrid(char[][] grid) {
        grid = new char[2*(numInRow-1)][1+2*(numInRow-1)];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                grid[i][j] = ' ';
            }
        }
        return grid;
    }
    /**
     * This method begins a game of ConnectX. It tells the UI to initialize a
     * game mode (playing against another player or the computer) based on
     * player choice and continues until someone wins.
     * @param console The ConnectX UI.
     * @param computer The computer player.
     */
    public void playGame(ConnectXTextConsole console, ConnectXComputerPlayer computer) {
        console.intializeGame(this);
        boolean endGame = isWin(currentPlayer,console.getGrid()) || isTie(console.getGrid());
        while(!endGame) {
            if(gameMode == 'P') {
                console.playAgainstPerson(this);
                endGame = isWin(currentPlayer,console.getGrid()) || isTie(console.getGrid());
            } else {
                console.playAgainstComputer(this, computer);
                endGame = isWin(currentPlayer,console.getGrid()) || isTie(console.getGrid());
                computer.computerTurn(this, console);
                endGame = isWin(currentPlayer,console.getGrid()) || isTie(console.getGrid());
            }
        }
    }
}

