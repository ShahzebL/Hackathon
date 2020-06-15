import java.util.*;
import java.io.*;

/**
 * first to get a 4 letter or longer word wins
 */
public class WordGameWithBot {

    private boolean[][] validElements;
    private int[] lastSpaceFilled; // index 0 represents row, 1 reps col
    private int currentPlayer;
    private boolean isPlayer1;
    private boolean playingWithBot;
    private boolean botsTurn;
    private String[][] wordGrid;
    private ArrayList<String> wordDictionary;
    private ArrayList<String> botDictionary;
    private boolean isGridFull;
    private boolean playing;

    /**
     * Constructor!!!!
     */
    public WordGameWithBot() throws IOException {
        playing = true;
        System.out.println("WORD-DUEL");
        System.out.println("-----------");
        System.out.println("The object of this game is to create a word before your opponent. \nA word must:");
        System.out.println("    read left to right, top to bottom, or left to right diagonals");
        System.out.println("    be of length greater than or equal to 4 letters");
        System.out.println("    stand alone");
        System.out.println("For example, \"gboat\" and \"afoodg\" are not words, but \"boat\" is.");
        System.out.println("-----------");
        System.out.println();

        System.out.println("Enter '0' for pass and play, enter '1' to play against bot!");
        Scanner scan = new Scanner(System.in);
        String inputC;
        int choice;
        inputC = scan.next(); // add error check
        while (inputC.compareTo("0") < 0 && inputC.compareTo("1") > 0) {
            System.out.println("input zero or one!");
            inputC = scan.next();
        }
        choice = Integer.parseInt(inputC);

        // assumming choice is 0 or 1
        if (choice == 1) {
            playingWithBot = true;
        } else {
            currentPlayer = 1;
            isPlayer1 = true;
        }
        lastSpaceFilled = new int[2];
        validElements = new boolean[11][11];
        for (int i = 0; i < 11; i++) {
            validElements[i][0] = false;
            validElements[0][i] = false;
        }
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                validElements[i][j] = true;
            }
        }
        wordGrid = new String[11][11]; // the borders represent the rows and cols
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                wordGrid[i][j] = " ";
            }
        }
        for (Integer i = 0; i < 11; i++) {
            wordGrid[i][0] = "" + i;
            wordGrid[0][i] = "" + i;
        }
        readDictWords("wordList.txt");
    }

    /**
     * this method plays the game
     */
    public void play() {
        updateDisplay();

        while (playing) {
            if (playingWithBot) {
                // add bot method here
                playWithBot();
                botsTurn = !botsTurn;
                //System.out.println("is Game over? " + isGameOver());
            } else {
                takeTurn();
                if (isPlayer1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }
                isPlayer1 = !isPlayer1;
            }
        }

        //System.out.println("Reached");

        System.out.println("---------------");
        if (winner() == 0) {
            System.out.println("Grid is full!");
            System.out.println("TIE GAME");
        } else {
            if (winner() == 3) {
                System.out.println("Player Bot wins!");
            } else {
                int wins = 1;
                if(currentPlayer==1)
                    wins = 2;
                System.out.println("Player " + wins + " wins!");
            }
        }
    }

    /**
     * equivalent of takeTurn() just with a bot
     */
    public void playWithBot() {
        if (!botsTurn) {
            Scanner scan = new Scanner(System.in);
            String inputRow;
            String inputCol;
            int row;
            int column;
            String input;

            System.out.println("enter row: ");
            inputRow = scan.next();

            while (inputRow.compareTo(":") >= 0 || inputRow.compareTo("0") <= 0 || Integer.parseInt(inputRow) > 10
                    || Integer.parseInt(inputRow) < 1) {
                System.out.println("enter valid row: ");
                inputRow = scan.next();
            }
            row = Integer.parseInt(inputRow);
            lastSpaceFilled[0] = row;

            System.out.println("enter column: ");
            inputCol = scan.next();
            while (inputCol.compareTo(":") >= 0 || inputCol.compareTo("0") <= 0 || Integer.parseInt(inputCol) > 10
                    || Integer.parseInt(inputCol) < 0) {
                System.out.println("enter valid column: ");
                inputCol = scan.next();

            }

            column = Integer.parseInt(inputCol);
            lastSpaceFilled[1] = column;

            while (!validElements[row][column]) {
                System.out.println("space is filled. Enter valid coordinates.");
                System.out.println("enter row: ");
                inputRow = scan.next();
                while (inputRow.compareTo(":") >= 0 || inputRow.compareTo("0") <= 0 || Integer.parseInt(inputRow) > 10
                        || Integer.parseInt(inputRow) < 1) {
                    System.out.println("enter valid row: ");
                    inputRow = scan.next();
                }
                row = Integer.parseInt(inputRow);

                System.out.println("enter column: ");
                inputCol = scan.next();
                while (inputCol.compareTo(":") >= 0 || inputCol.compareTo("0") <= 0 || Integer.parseInt(inputCol) > 10
                        || Integer.parseInt(inputCol) < 0) {
                    System.out.println("enter valid column: ");
                    inputCol = scan.next();
                }
                column = Integer.parseInt(inputCol);
            }
            validElements[row][column] = false;

            System.out.println("enter letter: ");
            input = scan.next();
            while (input.length() > 1 || input == null || input == "" || input == " ") {
                System.out.println("enter valid letter: ");
                input = scan.next();
            }
            wordGrid[row][column] = input.toLowerCase();
            lastSpaceFilled[0] = row;
            lastSpaceFilled[1] = column;
            isGameOver();
        } else {
            System.out.println("\nBot playing...\n");
            // BOT LOGIC GOES HERE
            if (!tryToWin()) {
                tryToStaleMate();
            }
        }
        updateDisplay();
    }

    /**
     * Bot tries to win if 4 in a row word is possible
     */
    private boolean tryToWin() {
        String possibility = "";
        String letters = "abcdefghijklmnopqrstuvwxyz";

        // find last inputed positions
        int row = lastSpaceFilled[0];
        int col = lastSpaceFilled[1];

        // TRIES HORIZONTAL
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            col--;
        }
        col++; // starting index after moving backwards
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            possibility += wordGrid[row][col]; // should contain 3 letter string
            col++;
        }
        String temp = possibility;
        int preCol = col;
        for (int i = 0; i < letters.length(); i++) {
            possibility += letters.substring(i, i + 1);
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;

                validElements[row][col] = false;
                return true;
            }
            possibility = temp;
        }
        col = preCol - possibility.length() - 1;
        possibility = temp;
        for (int i = 0; i < letters.length(); i++) {
            possibility = letters.substring(i, i + 1) + possibility;
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp;
        }
        // for (int i = 0; i < letters.length(); i++) {
        // // try adding to end
        // possibility += letters.substring(i, i + 1);
        // // col is one beyond end of current word
        // System.out.println("poss HORI: "+possibility);
        // if (isWord(possibility) && row>0 && row < 11 && col >0 && col < 11)
        // {
        // System.out.println(possibility + " is "+isWord(possibility));
        // // found 'em
        // wordGrid[row][col] = letters.substring(i, i + 1);
        // lastSpaceFilled[0] = row;
        // lastSpaceFilled[1] = col;
        // if(isGameOver()) System.out.println("bot wins test");
        // validElements[row][col] = false;
        // return true;
        // }
        // // revert possibility and col

        // possibility = possibility.substring(0,possibility.length()-1);
        // // try adding to beginning
        // possibility = letters.substring(i, i+1) + possibility;

        // //col-=(possibility.length());
        // System.out.println(possibility);
        // if (isWord(possibility) && row>0 && row<11 &&col>0 &&col<11) {
        // // found 'em
        // System.out.println("brat is wrong place");
        // wordGrid[row][col] = letters.substring(i, i + 1);
        // lastSpaceFilled[0] = row;
        // lastSpaceFilled[1] = col;
        // if(isGameOver()) System.out.println("bot wins test");
        // validElements[row][col] = false;
        // return true;
        // }
        // //revert possibility
        // possibility = possibility.substring(1);
        // //col+=possibility.length();
        // }

        // CHECK VERTICAL AND DIAGONAL
        possibility = "";
        row = lastSpaceFilled[0];
        col = lastSpaceFilled[1];
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            row--;
        }
        row++; // starting index after moving backwards
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            possibility += wordGrid[row][col]; // should contain 3 letter string
            row++;
        }
        String temp2 = possibility;
        int preRow = row;
        for (int i = 0; i < letters.length(); i++) {
            possibility += letters.substring(i, i + 1);
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp2;
        }
        row = preRow - possibility.length() - 1;
        possibility = temp2;
        for (int i = 0; i < letters.length(); i++) {
            possibility = letters.substring(i, i + 1) + possibility;
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp2;
        }
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     row--;
        // }
        // row++; // starting index
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     possibility += wordGrid[row][col];
        //     row++;
        // }
        // // row is at one past ending index
        // for (int i = 0; i < letters.length(); i++) {
        //     // try adding to end
        //     possibility += letters.substring(i, i + 1);
        //     // row one beyond end of current word
        //     System.out.println("poss VERTICAL: " + possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         validElements[row][col] = false;
        //         return true;
        //     }
        //     // revert possibility and row
        //     possibility = possibility.substring(0, possibility.length() - 1);
        //     // try adding to beginning
        //     possibility = letters.substring(i, i + 1) + possibility;
        //     // row-=(possibility.length());
        //     System.out.println(possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         validElements[row][col] = false;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         return true;
        //     }
        //     // revert possibility
        //     possibility = possibility.substring(1);
        //     // row+=possibility.length();
        // }

        // DIAGONAL DOWN
        possibility = "";
        row = lastSpaceFilled[0];
        col = lastSpaceFilled[1];
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            row--;
            col--;
        }
        row++;
        col++; // starting index after moving backwards
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            possibility += wordGrid[row][col]; // should contain 3 letter string
            row++;
            col++;
        }
        String temp3 = possibility;
        int preRow2 = row;
        int preCol2 = col;
        for (int i = 0; i < letters.length(); i++) {
            possibility += letters.substring(i, i + 1);
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp3;
        }
        row = preRow2 - possibility.length() - 1;
        possibility = temp3;
        for (int i = 0; i < letters.length(); i++) {
            possibility = letters.substring(i, i + 1) + possibility;
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp3;
        }
        // possibility = "";
        // row = lastSpaceFilled[0];
        // col = lastSpaceFilled[1];
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     row--;
        //     col--;
        // }
        // row++; // starting index
        // col++; // starting index
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     possibility += wordGrid[row][col];
        //     row++;
        //     col++;
        // }
        // for (int i = 0; i < letters.length(); i++) {
        //     // try adding to end
        //     possibility += letters.substring(i, i + 1);
        //     // row one beyond end of current word
        //     System.out.println("poss DIAG_DOWN: " + possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         validElements[row][col] = false;
        //         return true;
        //     }
        //     // revert possibility and row
        //     possibility = possibility.substring(0, possibility.length() - 1);
        //     // try adding to beginning
        //     possibility = letters.substring(i, i + 1) + possibility;
        //     // row-=(possibility.length());
        //     System.out.println(possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         validElements[row][col] = false;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         return true;
        //     }
        //     // revert possibility
        //     possibility = possibility.substring(1);
        //     // row+=possibility.length();
        // }






        // // DIAGONAL UP
        possibility = "";
        row = lastSpaceFilled[0];
        col = lastSpaceFilled[1];
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            row++;
            col--;
        }
        row--;
        col++; // starting index after moving backwards
        while (row > 0 && row < 11 && col > 0 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
            possibility += wordGrid[row][col]; // should contain 3 letter string
            row--;
            col++;
        }
        String temp4 = possibility;
        int preRow3 = row;
        int preCol3 = col;
        for (int i = 0; i < letters.length(); i++) {
            possibility += letters.substring(i, i + 1);
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp4;
        }
        row = preRow3 - possibility.length() - 1;
        possibility = temp4;
        for (int i = 0; i < letters.length(); i++) {
            possibility = letters.substring(i, i + 1) + possibility;
            if (isWord(possibility) && row > 0 && row < 11 && col > 0 && col < 11) {
                wordGrid[row][col] = letters.substring(i, i + 1);
                lastSpaceFilled[0] = row;
                lastSpaceFilled[1] = col;
                playing = !playing;
                validElements[row][col] = false;
                return true;
            }
            possibility = temp4;
        }
        // possibility = "";
        // row = lastSpaceFilled[0];
        // col = lastSpaceFilled[1];
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     row++;
        //     col--;
        // }
        // row--; // starting index
        // col++; // starting index
        // while (row > 0 && col > 0 && row < 11 && col < 11 && letters.indexOf(wordGrid[row][col]) != -1) {
        //     possibility += wordGrid[row][col];
        //     row--;
        //     col++;
        // }
        // for (int i = 0; i < letters.length(); i++) {
        //     // try adding to end
        //     possibility += letters.substring(i, i + 1);
        //     // row one beyond end of current word
        //     System.out.println("poss DIAG_UP: " + possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         validElements[row][col] = false;
        //         return true;
        //     }
        //     // revert possibility and row
        //     possibility = possibility.substring(0, possibility.length() - 1);
        //     // try adding to beginning
        //     possibility = letters.substring(i, i + 1) + possibility;
        //     // row-=(possibility.length());
        //     System.out.println(possibility);
        //     if (isWord(possibility)) {
        //         // found 'em
        //         wordGrid[row][col] = letters.substring(i, i + 1);
        //         lastSpaceFilled[0] = row;
        //         lastSpaceFilled[1] = col;
        //         validElements[row][col] = false;
        //         if (isGameOver())
        //             System.out.println("bot wins test");
        //         validElements[row][col] = false;
        //         return true;
        //     }
        //     // revert possibility
        //     possibility = possibility.substring(1);
        //     // row+=possibility.length();
        // }
        return false;
    }

    /**
     * Bot tries to stale mate, or at least not give the player any opportunity
     */
    private void tryToStaleMate() {
        String rareLetters = "zqjxkvbywgpfmu";
        int randNum = (int) (Math.random() * rareLetters.length());
        String letter = rareLetters.substring(randNum, randNum + 1);

        int count = 2;
        int deltaRandRow = (int) (Math.random() * count - Math.random() * count);
        int deltaRandCol = (int) (Math.random() * count - Math.random() * count);
        int calcRow = lastSpaceFilled[0] + deltaRandRow;
        int calcCol = lastSpaceFilled[1] + deltaRandCol;
        while (calcRow < 1 || calcRow > 10 || calcCol < 1 || calcCol > 10 || validElements[calcRow][calcCol] == false) {
            deltaRandRow = (int) (Math.random() * count - Math.random() * count);
            deltaRandCol = (int) (Math.random() * count - Math.random() * count);
            calcRow = lastSpaceFilled[0] + deltaRandRow;
            calcCol = lastSpaceFilled[1] + deltaRandCol;

            count++;
        }
        wordGrid[calcRow][calcCol] = letter;
        lastSpaceFilled[0] = calcRow;
        lastSpaceFilled[1] = calcCol;
        validElements[calcRow][calcCol] = false;
        isGameOver();
    }

    /**
     * this method takes input from the vitual machine: it takes a row and column
     * for the current player and a letter
     */
    public void takeTurn() {
        if (!playing)
            return;
        Scanner scan = new Scanner(System.in);
        String inputRow;
        String inputCol;
        int row;
        int column;
        String input;
        int player = 1;

        if (!isPlayer1)
            player = 2;

        System.out.println("\n PLAYER " + player + "\n");

        System.out.println("enter row: ");
        inputRow = scan.next();

        while (inputRow.compareTo(":") >= 0 || inputRow.compareTo("0") <= 0 || Integer.parseInt(inputRow) > 10
                || Integer.parseInt(inputRow) < 1) {
            System.out.println("enter valid row: ");
            inputRow = scan.next();

        }
        row = Integer.parseInt(inputRow);
        lastSpaceFilled[0] = row;

        System.out.println("enter column: ");
        inputCol = scan.next();
        while (inputCol.compareTo(":") >= 0 || inputCol.compareTo("0") <= 0 || Integer.parseInt(inputCol) > 10
                || Integer.parseInt(inputCol) < 0) {
            System.out.println("enter valid column: ");
            inputCol = scan.next();

        }
        column = Integer.parseInt(inputCol);
        lastSpaceFilled[1] = column;

        while (validElements[row][column] == false) {
            System.out.println("space is filled. Enter valid coordinates.");
            System.out.println("enter row: ");
            inputRow = scan.next();
            while (inputRow.compareTo(":") >= 0 || inputRow.compareTo("0") <= 0 || Integer.parseInt(inputRow) > 10
                    || Integer.parseInt(inputRow) < 1) {
                System.out.println("enter valid row: ");
                inputRow = scan.next();
            }
            row = Integer.parseInt(inputRow);

            System.out.println("enter column: ");
            inputCol = scan.next();
            while (inputCol.compareTo(":") >= 0 || inputCol.compareTo("0") <= 0 || Integer.parseInt(inputCol) > 10
                    || Integer.parseInt(inputCol) < 0) {
                System.out.println("enter valid column: ");
                inputCol = scan.next();
            }
            column = Integer.parseInt(inputCol);
        }
        validElements[row][column] = false;

        System.out.println("enter letter: ");
        input = scan.next();
        while (input.length() > 1 || input == null || input == "" || input == " ") {
            System.out.println("enter valid letter: ");
            input = scan.next();
        }
        wordGrid[row][column] = input.toLowerCase();
        lastSpaceFilled[0] = row;
        lastSpaceFilled[1] = column;
        updateDisplay();
        System.out.println();
        // scan.close();
        isGameOver();
    }

    // reads in words from the dictionary into an arrayList
    public void readDictWords(String filename) throws IOException {

        BufferedReader f = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int numWords = Integer.parseInt(st.nextToken());

        wordDictionary = new ArrayList<String>();
        botDictionary = new ArrayList<String>();

        String token = "";

        for (int i = 0; i < numWords; i++) {
            st = new StringTokenizer(f.readLine());
            token = st.nextToken();
            if (token.length() >= 4) {
                wordDictionary.add(token);
            }

            if (token.length() == 4) {
                botDictionary.add(token);
            }
        }

        f.close();
    }

    // checks if text is a word
    private boolean isWord(String text) {

        return wordDictionary.contains(text);
    }

    private boolean isBotWord(String text) {
        return botDictionary.contains(text);
    }

    /**
     * @return whether the space has a letter
     */
    private boolean isFilledSpace(int row, int col) {
        return row > 0 && row < 11 && col > 0 && col < 11 && !validElements[row][col];
    }

    /**
     * Return whether the game has ended
     */
    private boolean isGameOver() {
        // System.out.println(isWord(findHorizontalString()));
        // System.out.println(isWord(findVerticalString()));
        boolean full = true;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {

                if (validElements[i][j] == true) {
                    full = false;
                    break;
                }
            }
        }
        isGridFull = full;
        // System.out.println(Arrays.toString(lastSpaceFilled));
        if (full || isWord(findHorizontalString()) || isWord(findVerticalString()) || isWord(findDiagonalDownString())
                || isWord(findDiagonalUpString())) {
            playing = false;
        }
        return full || isWord(findHorizontalString()) || isWord(findVerticalString())
                || isWord(findDiagonalDownString()) || isWord(findDiagonalUpString());
    }

    /**
     * @return the string created by the newly added element horizontally
     */
    private String findHorizontalString() {
        int row = lastSpaceFilled[0];
        int col = lastSpaceFilled[1];

        while (row > 0 && row < 11 && col > 0 && col < 11 && !validElements[row][col]) {
            col--;
        }
        col++;
        String ret = "";
        while (row > 0 && row < 11 && col > 0 && col < 11 && !validElements[row][col]) {
            ret += wordGrid[row][col];
            col++;
        }
        //System.out.println(ret);
        return ret;
    }

    /**
     * @return the string created by the newly added element diagonally up
     */
    private String findDiagonalUpString() {
        int row = lastSpaceFilled[0];
        int col = lastSpaceFilled[1];
        while (isFilledSpace(row, col)) {
            col--;
            row++;
        }
        row--;
        col++;
        String ret = "";
        while (isFilledSpace(row, col)) {
            ret += wordGrid[row][col];
            row--;
            col++;
        }
        // System.out.println(ret);
        return ret;
    }

    /**
     * @return the string created by the newly added element diagonally down
     */
    private String findDiagonalDownString() {
        int row = lastSpaceFilled[0];
        int col = lastSpaceFilled[1];
        while (isFilledSpace(row, col)) {
            col--;
            row--;
        }
        row++;
        col++;
        String ret = "";
        while (isFilledSpace(row, col)) {
            ret += wordGrid[row][col];
            row++;
            col++;
        }
        // System.out.println(ret);
        return ret;
    }

    /**
     * @return the string created by the newly added element horizontally
     */
    private String findVerticalString() {

        int row = lastSpaceFilled[0];
        int col = lastSpaceFilled[1];
        while (isFilledSpace(row, col)) {
            row--;
        }
        row++;
        String ret = "";
        while (isFilledSpace(row, col)) {
            ret += wordGrid[row][col];
            row++;
        }
        // System.out.println(ret);
        return ret;
    }

    /**
     * returns 0 if no one is winner returns 1 if p1 is winner returns 2 if p2 is
     * winner
     */
    private int winner() {
        if (!playingWithBot) {

            if (isGridFull)
                return 0;
            return currentPlayer;

        } else {
            if (isGridFull)
                return 0;
            if (botsTurn) {
                return 1;
            } else {
                return 3;
            }

        }

    }

    public void updateDisplay() {
        for (String[] arr : wordGrid) {
            System.out.println(Arrays.toString(arr));
        }
    }

}