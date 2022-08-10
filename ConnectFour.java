import java.util.Scanner;

/**
 * ConnectFour
 */
public class ConnectFour{
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        //creates the 2-D array for the game board
        char [][] gameBoard;
        gameBoard = new char[6][7];

        //initializing game board to blank chars
        for (int i= 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                gameBoard[i][j] = ' ';
            }
        }

        //creates an integer for storing the turn count
        int turns = 0;

        //char variable that holds the disk color for the turn
        String pTurn;

        //int variable to check if game has ended; if gameOver == 0, game continues
        int gameOver = 0;

        //displays gameboard at start
        displayBoard(gameBoard);

        //loop calls methods to run game until gameOver indicates win or draw
        do {
            //sets turns equal to result of turnCount method
            turns = turnCount(turns);

            //determines the color of disk being dropped on the turn
            if (turns % 2 == 0)
            {
                pTurn = "RED";
            }
            else
            {
                pTurn = "YELLOW";
            }

            gameBoard = playDisk(gameBoard, pTurn.charAt(0));

            displayBoard(gameBoard);

            gameOver = checkWin(gameBoard, pTurn.charAt(0));

        } while (gameOver == 0);

        input.close();        
    }


    /**
     * turnCount method: keeps track of whose turn it is
     */
    public static int turnCount(int tNum) {
        tNum++;
        return tNum;
    }


    /**
     * displayBoard method: shows current game board on the console
     */
    public static void displayBoard(char[][] board) {
        //displays col numbers at top of board
        System.out.println("    " + 0 + "" + "\t    " + 1 + "" + "\t    " + 2 + "" + "\t    " + 3 + "" + "\t    " + 4 + "" + "\t    " + 5 + "" + "\t    " + 6 + "");        
    
        for (int i = 0; i < board.length; i++)
        {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++)
            {
                if (j != board[i].length - 1)
                {
                    System.out.print("   " + board[i][j] + "\t|");
                }
                else
                {
                    System.out.println("   " + board[i][j] + "\t|");
                    System.out.println();
                }
            }
        }
    }


     /**
      * playDisk method: takes in a column selected by the player, then checks
      for an empty row in that column; if none exists, informs the player that
      the column is full and advises them to select another column. Once player has
      made a valid move, returns the updated board to main method
      */
      public static char[][] playDisk(char[][] board, char pColor) {
          //int stores player's choice of column
          int playerChoice = 0;
          
          //boolean indicates whether or not the player has chosen an empty column
          boolean turnSuccess = false;
          
          //string to indicate whose turn it is
          String tColor;

          if (pColor == 'Y')
          {
              tColor = "YELLOW";
          }
          else
          {
              tColor = "RED";
          }
         
          //checks that user is entering an integer and validates input
          do {
                //prompt user input
                System.out.println("Please choose a column (0-6) to drop the " + tColor + " disk: ");
                
                //checks that user is entering ONLY integers; if not prompts user to try again
                while (!input.hasNextInt())
                {
                    displayBoard(board);
                    System.out.println("Please enter only numbers (0-6).");
                    input.next();
                }

                //takes player input; if input is valid, exits do-while loop
                playerChoice = input.nextInt();

          } while (playerChoice > 6 || playerChoice < 0);

          //check for empty row in the selected column
          for (int i = board.length - 1; i >= 0; i--)
          {
              //if the space at the bottom of the selected column is empty, places disk; if not
              //continues to check next space up
              if (board[i][playerChoice] == ' ')
              {
                  board[i][playerChoice] = pColor;
                  turnSuccess = true;
                  break;
              }
              else
              {
                  continue;
              }
          }

          //if player has chosen a column that is full, prompts the player to choose again
          while (turnSuccess == false)
          {
              displayBoard(board);
              System.out.println("Please select a column with a vacant space: ");

                //same loop for validating input
                do {
                    while (!input.hasNextInt())
                    {
                        displayBoard(board);
                        System.out.println("Please enter only numbers (0-6).");
                        input.next();
                    }
                    playerChoice = input.nextInt();

                } while (playerChoice > 6 || playerChoice < 0);

                //same loop checks that new column selection has a vacant space
                for (int i = board.length - 1; i >= 0; i--)
                {
                    if (board[i][playerChoice] == ' ')
                    {
                        board[i][playerChoice] = pColor;
                        turnSuccess = true;
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
          }
          
          //returns updated board
          return board;
      }


      /**
       * checkWin method: called at the end of each player's turn, checks to see
       * if there are four of the same color vertically, horizontally, or diagonally;
       * if so, declares a winner, if not, game continues. Then checks to see if the
       * board is full with no winner; if so, declares the game a draw, if not, game
       * continues
       */
      public static int checkWin(char[][] board, char pColor) {
        /**
         * creates int variable to store game status; if winner == 0, no winner found; if winner == 1,
         * last player to move has won; if winner == -1, the game board is full with no winner
         */
        int winner = 0;

        //boolean to verify whether or not board is full
        boolean isFull = true;
        
        //checks for four consecutive disk of same color HORIZONTALLY
        for (int i = board.length - 1; i >= 0; i--)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (j + 3 < board[i].length - 1)
                {
                    if (board[i][j] == pColor && board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2] && board[i][j+2] == board[i][j+3])
                    {
                        winner = 1;
                        break;
                    }
                }
            }

            //if winner is found after searching a row, breaks from outer loop and stops searching
            if (winner == 1)
            {
                break;
            }
        }
        
        //checks for four consecutive disks of same color VERTICALLY
        for (int i = board.length - 1; i >= 0; i--)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (i - 3 >= 0)
                {
                    if (board[i][j] == pColor && board[i][j] == board[i-1][j] && board[i-1][j] == board[i-2][j] && board[i-2][j] == board[i-3][j])
                    {
                        winner = 1;
                        break;
                    }
                }
            }

            if (winner == 1)
            {
                break;
            }
        }

        //checks for four consecutive disks of same color ASCENDING DIAGONALLY (left to right)
        for (int i = board.length - 1; i >= 0; i--)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (i - 3 >= 0 && j + 3 <= board[i].length - 1)
                {
                    if (board[i][j] == pColor && board[i][j] == board[i-1][j+1] && board[i-1][j+1] == board[i-2][j+2] && board[i-2][j+2] == board[i-3][j+3])
                    {
                        winner = 1;
                        break;
                    }
                }
            }

            if (winner == 1)
            {
                break;
            }
        }

        //checks for four consecutive disks of same color DESCENDING DIAGONALLY (left to right)
        for (int i = board.length - 1; i >= 0; i--)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (i + 3 <= board.length - 1 && j + 3 <= board[i].length - 1)
                {
                    if (board[i][j] == pColor && board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3])
                    {
                        winner = 1;
                        break;
                    }
                }
            }

            if (winner == 1)
            {
                break;
            }
        }

        //checks to see if board is full by searching for any vacant spaces; if a vacant space is found
        //boolean isFull is set to false and game can continue
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == ' ')
                {
                    isFull = false;
                }
            }
        }

        //if a winner has been found on red player's turn, prints out a message saying red player wins
        if (winner == 1 && pColor == 'R')
        {
            System.out.println("RED WINS!");
        }

        //if winner is found on yellow player's turn, prints out message saying yellow player wins
        if (winner == 1 && pColor == 'Y')
        {
            System.out.println("YELLOW WINS!");
        }

        //if no winner is found and the board is full, prints out a message saying match ends in draw
        if (isFull == true)
        {
            winner = -1;
            System.out.println("DRAW!");
        }

        return winner;
      }

}