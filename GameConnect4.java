/* Ben Costas
Connect 4 Game (including AI)
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameConnect4 extends JFrame implements ActionListener{
    //Create Panels
    private JPanel slotsPanel = new JPanel();
    //Create Buttons
    private JButton[][] slots = new JButton[6][7];
    //Create the "console game board" - used to check if player has won + used to display information to the GUI
    private String[][] gameBoard = new String[6][7];
    private int playerTurn = 0;
    //Initialize global variable for the mode
    private int mode;
    private boolean AIgame = false;
    //Initialize global variable for when the game is finished
    private boolean gameComplete = true;

    public GameConnect4(int mode){
        this.mode = mode;//Initialize the gamemode as a global variable
        if (mode == 2) {
            AIgame = true;
        }
        displayBoard();
    }

    public void displayBoard() {
        //Set frame components
        setSize(700,600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        //Set Layouts
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        slotsPanel.setLayout(new GridLayout(6,7));

        //Set "console game board" to "." - filler variables and slots
        for (int i = 0; i < gameBoard.length; i++) {
            for (int k = 0; k < gameBoard[0].length; k++) {
                slots[i][k] = new JButton();
                slots[i][k].addActionListener(this);
                gameBoard[i][k] = ".";
                slotsPanel.add(slots[i][k]);
            }
        }
        add(slotsPanel);
        setVisible(true);  
    }
    public void actionPerformed(ActionEvent e) {//Called when button is clicked
        for (int i = 0; i < slots.length; i++) {
            for (int k = 0; k < slots[0].length; k++) {
                if (e.getSource() == slots[i][k] && gameComplete == true) {//Identify which button was clicked
                    updateMove(k);//Pass the column of the button click into updateMove()
                }
            }
        }
    }
    public void updateMove(int column) {//Update the board based on the column that was clicked
        boolean turnEnd = true;
        while(turnEnd == true) {
            if (!gameBoard[0][column].equals(".")) { // If the entire column is full, exit loop (get another entry)
                if (playerTurn%2 == 1) {
                    setTitle("Player 1's Move - Column is full!");
                }
                else {
                    setTitle("Player 2's Move - Column is full!");
                }
                break;
            } //If the column is not full:
            else {
                for (int i = slots.length-1; i >= 0; i--) {//Count starting from the bottom of the column
                    if (gameBoard[i][column].equals(".")) {//If the space is empty
                        slots[i][column].setText("O");//Add a disc
                        playerTurn++;
                        if (AIgame == true) {
                            setTitle("Player 1's Move");
                            slots[i][column].setForeground(Color.RED);
                            gameBoard[i][column] = "1";
                            playerTurn++;
                            AI();
                        }
                        
                        else {
                            if (playerTurn%2 == 1) {
                                setTitle("Player 1's Move");
                                slots[i][column].setForeground(Color.RED);
                                gameBoard[i][column] = "1";
                            }
                            if (playerTurn%2 == 0) {
                                setTitle("Player 2's Move");
                                slots[i][column].setForeground(Color.BLUE);
                                gameBoard[i][column] = "2";
                            }
                            
                        }
                        checkWin(gameBoard[i][column]);
                        break;
                    }
                }
                System.out.println("# of turns: " +playerTurn);
                for (int i = 0 ; i < gameBoard.length; i++) {
                    for (int k = 0; k < gameBoard[0].length; k++) {
                        System.out.print(gameBoard[i][k]);
                    }
                    System.out.println();
                }
                turnEnd = false;
            }
        }
    }

   public void checkWin(String playerSymbol) {
        //Win - check the last disc that was played
        //Initialize x and y components = downwards, right, downwards right diagonal, downwards left diagonal
        int[] xComponent = {0, 1, 1, -1};
        int[] yComponent = {-1, 0, -1, -1};

        for (int i = 0; i < gameBoard.length; i++) {
            for (int k = 0; k < gameBoard[0].length; k++) {
                //Check discs of the last player
                if (gameBoard[i][k].equals(playerSymbol)) {
                    for (int dir = 0; dir < xComponent.length; dir++) {
                        String checkFour = playerSymbol;
                        //Initialize starting variables
                        int startX = i;
                        int startY = k;
                        //Search surrounding letters
                        for (int r = 0; r < 3; r++) {
                            startX += xComponent[dir];
                            startY += yComponent[dir];
                            if (startX < 0 || startX > gameBoard.length-1 || startY < 0 || startY > gameBoard[0].length-1) {//If components go out of bounds
                                break; //Check the next direction or break
                            }
                            checkFour += gameBoard[startX][startY];
                            if (checkFour.equals(playerSymbol + playerSymbol + playerSymbol + playerSymbol)) {// If 4 in a row
                                setTitle("Player " + playerSymbol + " Won! -" + playerTurn + " moves were played this game!");
                                gameComplete = false;
                                Connect4 connect4 = new Connect4();
                                break;
                            }
                        }
                    }
                }
                else {
                    continue;
                }
            }
        }
        int tieCheck = 0;
        //Tie - check top row - if all indexes are full (not ".") - game is a tie - win condition is already checked above
        for (int k = 0 ; k < gameBoard[0].length; k++) {
            if (!gameBoard[0][k].equals(".")) {
                tieCheck++;
            }
            if (tieCheck == 7) {
                setTitle("Tie Game!");
                gameComplete = false;
            }
        }
    }

    public void AI() {
        /*
        Determine the possible positions the AI can play
        Determine the "score" of the possible positions
        Which ever position has the highest score is what will be played
        */

        //Initialize values to store the best score and the best positions
        int highestScore = 0;
        int bestI = 0;
        int bestK = 0;
        
        //Initialize Direction Vectors - down, right, left, right down diag, left down diag, right up diag, left up diag
        int[] xComponent = {0, 1, -1, 1, -1, 1, -1};
        int[] yComponent = {-1, 0, 0,-1, -1, 1, 1};

        //Determine playable positions
        for (int i = 0; i < gameBoard.length; i++) {
            for (int k = 0; k < gameBoard[0].length; k++) {
                //Checks playable positions - if the bottom of the column is empty or if the piece below the given slot is full and the current piece is empty: 
                if ((i == gameBoard.length-1 && gameBoard[i][k].equals(".")) || (gameBoard[i][k].equals(".") && !gameBoard[i+1][k].equals("."))) {

                    int score = 0;//create new score

                    for (int dir = 0; dir < xComponent.length; dir++) {
                        String check = "";
                        //Initialize starting variables
                        int startX = i;
                        int startY = k;
                        //Search surrounding letters
                        for (int r = 0; r < 3; r++) {
                            startX += xComponent[dir];
                            startY += yComponent[dir];
                            if (startX < 0 || startX > gameBoard.length-1 || startY < 0 || startY > gameBoard[0].length-1) {//If components go out of bounds
                                break; //Check the next direction or break
                            }
                            check += gameBoard[startX][startY];
                            if (check.equals("222")) { //If able win the game - first priority - WIN
                                setTitle("AI has won! -" + playerTurn + " moves were played this game!");
                                gameBoard[i][k] = "2";
                                slots[i][k].setText("O");
                                slots[i][k].setForeground(Color.BLUE);
                                gameComplete = false;
                                Connect4 connect4 = new Connect4();
                                return;

                            }
                            if (check.equals("111")) {//If able to stop opponent from winning the game - second priority
                                score += 50;
                            }
                            if (check.equals("11")) {//If able to stop opponent from creating 3 in a row 
                                score += 20;
                            }
                            if (check.equals("22")) {//if able to make our own 3 in a row
                                score += 15;
                            }
                            if (check.equals("1") || check.equals("2") || check.equals("2.")) {
                                score += 5;
                            }
                            else {
                                score += 1;
                            }
                        }
                        if (score > highestScore) {//If the score for the avaiable position is higher than the any of the previous scores
                            highestScore = score;
                            bestI = i; //Set the position of the given score as the best possible case for the AI
                            bestK = k;
                        }
                    }
                }
                else {//If the slot is already filled in
                    continue;
                }
            }
        }
        gameBoard[bestI][bestK] = "2";
        slots[bestI][bestK].setText("O");
        slots[bestI][bestK].setForeground(Color.BLUE);
    }
}