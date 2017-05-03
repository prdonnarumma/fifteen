/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fifteen;
import java.util.*;


public class Fifteen {

    public static final int DIM_MIN = 3, DIM_MAX = 9;
    
    public static void main(String[] args) {
        //variables
        int d;
        int [][] board;
        Scanner reader = new Scanner(System.in);
        Fifteen fifteen = new Fifteen();
        
        //prompts for board size
        System.out.print("What is the board size: ");
        d = reader.nextInt();
        
        //initilises board array
        board = new int [d][d];
        
        if (d < DIM_MIN || DIM_MAX < d) {
            System.out.printf("\nBoard must be beteewwn %d x %d and %d x %d, inclusive.\n", DIM_MIN, DIM_MIN, DIM_MAX, DIM_MAX);
            System.exit(1);
        }
        
        fifteen.clear();
        
        System.out.println("WELCOME TO THE GAME OF FIFTEEN\n");
        try {
                    Thread.sleep(1000);   
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
        //open log, here will be saved all the moves the user makes
        
        //initialise the board
        fifteen.init(board, d);
        
        //accept moves until game is won
        while (true) {
            //clear the screen
            fifteen.clear();
            
            //draw the current state of the board
            fifteen.draw(board);
            
            //log the current state of the board (for testing)
            //code
            
            //check for win
            if (fifteen.won(board)) {
                System.out.println("You won!");
                break;
            }
            
            //prompt for move
            System.out.print("Tile to move: ");
            int tile = reader.nextInt();
            
            if (tile == 0) {
                break;
            }
            
            //log move (for testing)
            //code
            
            if (!fifteen.move(board,tile)) {
                System.out.println("\nIllegal move.\n");
                try {
                    Thread.sleep(1000);   
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            
            //sleep thread for animation's sake
            try {
                    Thread.sleep(1000);   
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            
        }
        
        //close log
        //code
        
        //success
        System.exit(0);
    }

    private void clear() {
        System.out.print("\033[2J");
        System.out.printf("\033[%d;%dH", 0,0);
    }
    
    private void draw(int board[][]) {
        int j,i,k;
        StringBuilder sb = new StringBuilder();
        for (i=0; i <= board.length-1; i++) {
            for (k=0; k <board.length;k++) {
                 sb.append("+----");
                if (k == board.length-1) {
                    sb.append("+\n");
                }
            }
            for (j=0; j <= board.length-1; j++){
                if (board[i][j] <= 9){
                    sb.append("|  ");
                    if (board[i][j] == 0) {
                        sb.append("_");
                    }
                    else {
                        sb.append(board[i][j]);
                    }
                    sb.append(" ");
                }
                else {
                    sb.append("| ");
                    sb.append(board[i][j]);
                    sb.append(" ");
                }
                if (j == board.length-1) {sb.append("|");}
            }
            sb.append("\n");
        }
        for (k=0; k <board.length;k++) {
             sb.append("+----");
            if (k == board.length-1) {
                    sb.append("+\n");
            }    
        }
        System.out.print(sb.toString());
    }
    
    private void init(int board[][], int d) {
        int corn = (d)*(d) - 1;
        int j,i,temp;
        for (i=0; i <= d-1; i++) {
            for (j=0; j <= d-1; j++){
                board[i][j]=corn;
                corn--;
            }
        }
        
        if ((d%2) == 0) {
            temp = board[d-1][(d-2)];
            board[d-1][(d-2)] = board[d-1][(d-3)];
            board[d-1][(d-3)] = temp;
        }
        
    }
    
    private boolean move(int board [][], int tile) {
        int i,j,n,m=0,temp;
        for (i = 0 ; i < board.length; i++) {
            for (j = 0; j < board.length; j++) {
                if (tile == board[i][j] && this.canMove(board,i,j)){
                innerloop:
                    for (n = 0 ; n <board.length; n++) {
                        for (m = 0; m < board.length; m++) {
                            if (board[n][m] == 0) {
                            break innerloop;}
                        }
                    }
                temp = board[n][m];
                board[n][m] = board [i][j];
                board[i][j] = temp;
                return true;
                }
            }
        }
        return false;
    }
    
    private boolean canMove(int board[][], int i, int j) {
        boolean a,b,c,d;
        try {
            a = (board[i][j-1] == 0);
        }
        catch(Exception e) {
            a = false;
        }
        try {
            b = board[i-1][j] == 0;
        }
        catch(Exception e) {
            b = false;
        }
        try {
            c = board[i][j+1] == 0;
        }
        catch(Exception e){
            c = false;
        }
        try {
            d = board[i+1][j] == 0;
        }
        catch (Exception e) {
            d = false;
        }
        if (a || b || c || d) {
         return true;   
        }
        else {return false;}
    }
    
    private boolean won(int board[][]) {
        int corn = 1;
        int win[][] = new int [board.length][board.length];
        int j,i;
        
        //creates winning board
        for (i = 0; i <= win.length-1; i++) {
            for (j=0; j <= (win.length-1); j++){
                win[i][j]=corn;
                corn++;
            }
        }
        win[win.length-1][win.length-1] = 0;
        //compares winning board whit user's board
        for (i = 0; i <= board.length-1; i++) {
            for (j = 0; j <= board.length-1; j++){
                if (board[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
